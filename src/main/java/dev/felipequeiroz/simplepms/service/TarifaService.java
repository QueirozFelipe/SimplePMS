package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.ClassificacaoHospede;
import dev.felipequeiroz.simplepms.domain.Tarifa;
import dev.felipequeiroz.simplepms.domain.TarifaDetalhamento;
import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import dev.felipequeiroz.simplepms.dto.CadastroTarifaDTO;
import dev.felipequeiroz.simplepms.dto.CadastroTarifaDetalhamentoDTO;
import dev.felipequeiroz.simplepms.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    public Tarifa cadastrar(CadastroTarifaDTO dto) {

        // Obter todos os valores possíveis do enum ClassificacaoHospede
        List<ClassificacaoHospede> tiposEnum = Arrays.asList(ClassificacaoHospede.values());

        // Verificar se o número de detalhes fornecidos corresponde ao número de tipos do enum
        if (dto.tarifaDetalhamentos().size() != tiposEnum.size()) {
            throw new IllegalArgumentException("É necessário fornecer um detalhamento para cada classificação de hóspede.");
        }

        // Verificar se todos os tipos do enum estão presentes e se não há duplicatas
        List<ClassificacaoHospede> classificacoesFornecidas = dto.tarifaDetalhamentos().stream()
                .map(CadastroTarifaDetalhamentoDTO::classificacaoHospede)
                .distinct()
                .collect(Collectors.toList());

        if (!classificacoesFornecidas.containsAll(tiposEnum)) {
            throw new IllegalArgumentException("Cada tipo de classificação de hóspede (CRIANCA, ADOLESCENTE, ADULTO) deve ter um detalhamento.");
        }

        // Criar a entidade Tarifa
        Tarifa tarifa = new Tarifa();
        tarifa.setNomeTarifa(dto.nomeTarifa());
        tarifa.setValorBase(dto.valorBase());

        // Adicionar os detalhes à tarifa
        List<TarifaDetalhamento> detalhes = dto.tarifaDetalhamentos().stream().map(detalheDTO -> {
            TarifaDetalhamento detalhe = new TarifaDetalhamento();
            detalhe.setClassificacaoHospede(detalheDTO.classificacaoHospede());
            detalhe.setValorHospedeAdicional(detalheDTO.valorHospedeAdicional());
            detalhe.setTarifa(tarifa);
            return detalhe;
        }).collect(Collectors.toList());

        tarifa.setTarifaDetalhamentos(detalhes);

        // Salvar a tarifa com seus detalhes
        return tarifaRepository.save(tarifa);

    }

    public URI criarUri(Tarifa tarifa, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("tarifas/{id}").buildAndExpand(tarifa.getId()).toUri();
    }
}

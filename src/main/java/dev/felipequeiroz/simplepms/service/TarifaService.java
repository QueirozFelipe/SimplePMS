package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.Tarifa;
import dev.felipequeiroz.simplepms.domain.TarifaDetalhamento;
import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import dev.felipequeiroz.simplepms.dto.tarifa.AtualizacaoTarifaDTO;
import dev.felipequeiroz.simplepms.dto.tarifa.AtualizacaoTarifaDetalhamentoDTO;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDTO;
import dev.felipequeiroz.simplepms.dto.tarifa.DetalhamentoTarifaDetalhamentoDTO;
import dev.felipequeiroz.simplepms.repository.TarifaDetalhamentoRepository;
import dev.felipequeiroz.simplepms.repository.TarifaRepository;
import dev.felipequeiroz.simplepms.validations.tarifa.CadastrarTarifaValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private TarifaDetalhamentoRepository tarifaDetalhamentoRepository;

    @Autowired
    private List<CadastrarTarifaValidations> validationsList;

    public Tarifa cadastrar(CadastroTarifaDTO dto) {

        validationsList.forEach(v -> v.validate(dto));

        Tarifa tarifa = new Tarifa(dto);

        List<TarifaDetalhamento> tarifaDetalhamentos = dto.tarifaDetalhamentos().stream().map(detalhamentoDTO -> {
            TarifaDetalhamento detalhamento = new TarifaDetalhamento();
            detalhamento.setClassificacaoHospede(detalhamentoDTO.classificacaoHospede());
            detalhamento.setValorHospedeAdicional(detalhamentoDTO.valorHospedeAdicional());
            detalhamento.setTarifa(tarifa);
            return detalhamento;
        }).collect(Collectors.toList());

        tarifa.setTarifaDetalhamentos(tarifaDetalhamentos);

        return tarifaRepository.save(tarifa);

    }

    public URI criarUri(Tarifa tarifa, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("tarifas/{id}").buildAndExpand(tarifa.getId()).toUri();
    }

    public Tarifa atualizar(AtualizacaoTarifaDTO dto) {

        Tarifa tarifa = tarifaRepository.getReferenceById(dto.id());

        Optional.ofNullable(dto.nomeTarifa()).ifPresent(tarifa::setNomeTarifa);
        Optional.ofNullable(dto.valorBase()).ifPresent(tarifa::setValorBase);
        Optional.ofNullable(dto.tarifaDetalhamentos()).ifPresent(detalhamentos -> atualizarDetalhamento(dto, detalhamentos));

        return tarifa;

    }

    public void atualizarDetalhamento(AtualizacaoTarifaDTO tarifaDTO, List<AtualizacaoTarifaDetalhamentoDTO> detalhamentos) {
        for (AtualizacaoTarifaDetalhamentoDTO atualizacaoTarifaDetalhamentoDTO : detalhamentos) {
            TarifaDetalhamento detalhamento = tarifaDetalhamentoRepository.findByTarifaIdAndClassificacaoHospede(tarifaDTO.id(), atualizacaoTarifaDetalhamentoDTO.classificacaoHospede());
            detalhamento.setValorHospedeAdicional(atualizacaoTarifaDetalhamentoDTO.valorHospedeAdicional());

        }
    }
}

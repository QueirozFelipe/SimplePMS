package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.Tarifa;
import dev.felipequeiroz.simplepms.domain.TarifaDetalhamento;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDTO;
import dev.felipequeiroz.simplepms.repository.TarifaRepository;
import dev.felipequeiroz.simplepms.validations.tarifa.CadastrarTarifaValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

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
}

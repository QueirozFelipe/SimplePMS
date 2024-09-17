package dev.felipequeiroz.simplepms.dto;

import dev.felipequeiroz.simplepms.domain.ClassificacaoHospede;
import dev.felipequeiroz.simplepms.domain.TarifaDetalhamento;

import java.math.BigInteger;

public record DetalhamentoTarifaDetalhamentoDTO(
        ClassificacaoHospede classificacaoHospede,
        BigInteger valorHospedeAdicional
) {
    public DetalhamentoTarifaDetalhamentoDTO(TarifaDetalhamento tarifaDetalhamento) {
        this(tarifaDetalhamento.getClassificacaoHospede(), tarifaDetalhamento.getValorHospedeAdicional());
    }
}

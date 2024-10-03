package dev.felipequeiroz.simplepms.dto.tarifa;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record AtualizacaoTarifaDTO(
        @NotNull
        Long id,
        String nomeTarifa,
        BigDecimal valorBase,
        List<AtualizacaoTarifaDetalhamentoDTO> tarifaDetalhamentos
) {
}

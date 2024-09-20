package dev.felipequeiroz.simplepms.dto.tarifa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CadastroTarifaDTO(
        @NotBlank
        String nomeTarifa,
        @NotNull
        BigDecimal valorBase,
        List<CadastroTarifaDetalhamentoDTO> tarifaDetalhamentos
) {
}

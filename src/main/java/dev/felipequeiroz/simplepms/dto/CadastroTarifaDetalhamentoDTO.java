package dev.felipequeiroz.simplepms.dto;

import dev.felipequeiroz.simplepms.domain.ClassificacaoHospede;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record CadastroTarifaDetalhamentoDTO(
        @NotBlank
        ClassificacaoHospede classificacaoHospede,
        @NotNull
        BigInteger valorHospedeAdicional
) {
}

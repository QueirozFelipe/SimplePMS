package dev.felipequeiroz.simplepms.dto.tarifa;

import dev.felipequeiroz.simplepms.domain.ClassificacaoHospede;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record AtualizacaoTarifaDetalhamentoDTO(
        @NotNull
        ClassificacaoHospede classificacaoHospede,
        BigInteger valorHospedeAdicional
) {
}

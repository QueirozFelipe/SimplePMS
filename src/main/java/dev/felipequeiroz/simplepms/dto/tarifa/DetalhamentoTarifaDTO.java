package dev.felipequeiroz.simplepms.dto.tarifa;

import dev.felipequeiroz.simplepms.domain.Tarifa;
import dev.felipequeiroz.simplepms.dto.tarifa.DetalhamentoTarifaDetalhamentoDTO;

import java.math.BigDecimal;
import java.util.List;

public record DetalhamentoTarifaDTO(
        Long id,
        String nomeTarifa,
        BigDecimal valorBase,
        List<DetalhamentoTarifaDetalhamentoDTO> detalhamentosTarifaDTO
) {
    public DetalhamentoTarifaDTO(Tarifa tarifa) {
        this(tarifa.getId(), tarifa.getNomeTarifa(), tarifa.getValorBase(), tarifa.getTarifaDetalhamentos().stream().map(DetalhamentoTarifaDetalhamentoDTO::new).toList());
    }
}

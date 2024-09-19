package dev.felipequeiroz.simplepms.validations.tarifa;

import dev.felipequeiroz.simplepms.domain.ClassificacaoHospede;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDTO;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDetalhamentoDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TodasClassificacoesPresentesValidation implements CadastrarTarifaValidations {

    @Override
    public void validate(CadastroTarifaDTO dto) {

        List<ClassificacaoHospede> tiposEnum = Arrays.asList(ClassificacaoHospede.values());

        List<ClassificacaoHospede> classificacoesFornecidas = dto.tarifaDetalhamentos().stream()
                .map(CadastroTarifaDetalhamentoDTO::classificacaoHospede)
                .distinct()
                .collect(Collectors.toList());

        if (!classificacoesFornecidas.containsAll(tiposEnum)) {
            throw new IllegalArgumentException("Deve ser preenchido um valor adicional para cada classificação de hóspede.");
        }

    }
}

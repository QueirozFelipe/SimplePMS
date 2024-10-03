package dev.felipequeiroz.simplepms.validations.tarifa;

import dev.felipequeiroz.simplepms.domain.ClassificacaoHospede;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDTO;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDetalhamentoDTO;
import dev.felipequeiroz.simplepms.repository.TarifaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TodasClassificacoesPresentesValidationTest {

    @Mock
    private TarifaRepository repository;
    @Mock
    private CadastroTarifaDTO dto;
    @InjectMocks
    private TodasClassificacoesPresentesValidation validation;

    @Test
    @DisplayName("Deve lançar exception se não foram cadastrados detalhamentos para cada classificação")
    void validacaoDetalhamentoClasificacaoAusente() {

        CadastroTarifaDTO dto = new CadastroTarifaDTO("Tarifa", new BigDecimal("100.00"),
                List.of(new CadastroTarifaDetalhamentoDTO(ClassificacaoHospede.ADULTO, new BigInteger("50"))));

        assertThrows(IllegalArgumentException.class, () -> validation.validate(dto));

    }

    @Test
    @DisplayName("Não deve lançar exception se foram cadastrados detalhamentos para cada classificação")
    void validacaoDetalhamentoClasificacoesPresentes() {

        CadastroTarifaDTO dto = new CadastroTarifaDTO("Tarifa", new BigDecimal("100.00"),
                Arrays.asList((new CadastroTarifaDetalhamentoDTO(ClassificacaoHospede.CRIANCA, new BigInteger("50"))),
                              (new CadastroTarifaDetalhamentoDTO(ClassificacaoHospede.ADOLESCENTE, new BigInteger("50"))),
                              (new CadastroTarifaDetalhamentoDTO(ClassificacaoHospede.ADULTO, new BigInteger("50")))));

        assertDoesNotThrow(() -> validation.validate(dto));

    }

}
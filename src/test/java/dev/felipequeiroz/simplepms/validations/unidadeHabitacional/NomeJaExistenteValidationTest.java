package dev.felipequeiroz.simplepms.validations.unidadeHabitacional;

import dev.felipequeiroz.simplepms.dto.CadastroClienteDTO;
import dev.felipequeiroz.simplepms.dto.CadastroUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.exception.BusinessValidationException;
import dev.felipequeiroz.simplepms.repository.ClienteRepository;
import dev.felipequeiroz.simplepms.repository.UnidadeHabitacionalRepository;
import dev.felipequeiroz.simplepms.validations.cliente.DocumentoJaExistenteValidation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NomeJaExistenteValidationTest {

    @Mock
    private UnidadeHabitacionalRepository repository;
    @Mock
    private CadastroUnidadeHabitacionalDTO dto;
    @InjectMocks
    private NomeJaExistenteValidation validation;

    @Test
    @DisplayName("Deve lançar exception se o nome já existe no banco de dados")
    void validacaoNoneJaCadastrado() {

        BDDMockito.given(repository.existsByNomeUhIgnoreCase(dto.nomeUh())).willReturn(true);

        assertThrows(BusinessValidationException.class, () -> validation.validate(dto));

    }

    @Test
    @DisplayName("Não deve lançar exception se o nome não existe no banco de dados")
    void validacaoNomeNaoCadastrado() {

        BDDMockito.given(repository.existsByNomeUhIgnoreCase(dto.nomeUh())).willReturn(false);

        assertDoesNotThrow(() -> validation.validate(dto));

    }

}
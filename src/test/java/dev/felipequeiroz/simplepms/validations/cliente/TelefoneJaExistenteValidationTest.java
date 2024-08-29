package dev.felipequeiroz.simplepms.validations.cliente;

import dev.felipequeiroz.simplepms.dto.CadastroClienteDTO;
import dev.felipequeiroz.simplepms.exception.BusinessValidationException;
import dev.felipequeiroz.simplepms.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TelefoneJaExistenteValidationTest {

    @Mock
    private ClienteRepository repository;

    @Mock
    private CadastroClienteDTO dto;

    @InjectMocks
    private TelefoneJaExistenteValidation validation;

    @Test
    @DisplayName("Deve lancar exception se o telefone informado não for null e já existir no banco de dados")
    void validacaoTelefoneJaCadastrado() {

        BDDMockito.given(dto.telefone()).willReturn("47912341234");
        BDDMockito.given(repository.existsByTelefone(dto.telefone())).willReturn(true);

        assertThrows(BusinessValidationException.class, () -> validation.validate(dto));
    }

    @Test
    @DisplayName("Não deve lancar exception se o telefone informado ja existir no banco de dados mas for null")
    void validacaoTelefoneJaCadastradoNull() {

        BDDMockito.given(dto.telefone()).willReturn(null);

        assertDoesNotThrow(() -> validation.validate(dto));
    }

    @Test
    @DisplayName("Não deve lancar exception se o telefone informado não for null e não existir no banco de dados")
    void validacaoTelefoneNaoCadastrado() {

        BDDMockito.given(dto.telefone()).willReturn("47912341234");
        BDDMockito.given(repository.existsByTelefone(dto.telefone())).willReturn(false);

        assertDoesNotThrow(() -> validation.validate(dto));
    }

}
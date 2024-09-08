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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DocumentoJaExistenteValidationTest {

    @Mock
    private ClienteRepository repository;
    @Mock
    private CadastroClienteDTO dto;
    @InjectMocks
    private DocumentoJaExistenteValidation validation;

    @Test
    @DisplayName("Deve lançar exception se o documento já existe no banco de dados")
    void validacaoDocumentoJaCadastrado() {

        BDDMockito.given(repository.existsByDocumento(dto.documento())).willReturn(true);

        assertThrows(BusinessValidationException.class, () -> validation.validate(dto));

    }

    @Test
    @DisplayName("Não deve lançar exception se o documento não existe no banco de dados")
    void validacaoDocumentoNaoCadastrado() {

        BDDMockito.given(repository.existsByDocumento(dto.documento())).willReturn(false);

        assertDoesNotThrow(() -> validation.validate(dto));

    }

}
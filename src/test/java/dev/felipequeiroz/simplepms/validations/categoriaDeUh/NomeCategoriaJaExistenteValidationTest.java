package dev.felipequeiroz.simplepms.validations.categoriaDeUh;

import dev.felipequeiroz.simplepms.dto.categoriaDeUh.CadastroCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.exception.BusinessValidationException;
import dev.felipequeiroz.simplepms.repository.CategoriaDeUhRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NomeCategoriaJaExistenteValidationTest {

    @Mock
    private CategoriaDeUhRepository repository;
    @Mock
    private CadastroCategoriaDeUhDTO dto;
    @InjectMocks
    private NomeCategoriaJaExistenteValidation validation;

    @Test
    @DisplayName("Deve lançar exception se o nome da categoria já existe no banco de dados")
    void validacaoNomeCategoriaJaCadastrado() {

        BDDMockito.given(repository.existsByNomeCategoriaIgnoreCase(dto.nomeCategoria())).willReturn(true);

        assertThrows(BusinessValidationException.class, () -> validation.validate(dto));

    }

}
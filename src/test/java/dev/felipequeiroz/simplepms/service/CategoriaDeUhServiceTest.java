package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import dev.felipequeiroz.simplepms.dto.CadastroCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.repository.CategoriaDeUhRepository;
import dev.felipequeiroz.simplepms.validations.categoriaDeUh.CadastrarCategoriaDeUhValidations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CategoriaDeUhServiceTest {

    @Mock
    CategoriaDeUhRepository repository;
    @Spy
    private CategoriaDeUh categoriaDeUh = new CategoriaDeUh();
    @Spy
    private List<CadastrarCategoriaDeUhValidations> validationsList = new ArrayList<>();
    @Mock
    private CadastrarCategoriaDeUhValidations validation1;
    @Mock
    private CadastrarCategoriaDeUhValidations validation2;
    @Captor
    private ArgumentCaptor<CategoriaDeUh> categoriaCaptor;
    @Mock
    private UriComponentsBuilder uriBuilder;
    @InjectMocks
    CategoriaDeUhService categoriaService;

    @Test
    void deveriaSalvarCategoriaDeUhAoCadastrar() {

        CadastroCategoriaDeUhDTO dto = new CadastroCategoriaDeUhDTO("Nome", 2);
        CategoriaDeUh categoria = new CategoriaDeUh(dto);

        categoriaService.cadastrar(dto);

        BDDMockito.then(repository).should().save(categoriaCaptor.capture());
        CategoriaDeUh categoriaSalva = categoriaCaptor.getValue();
        assertEquals(categoria.getNomeCategoria(), categoriaSalva.getNomeCategoria());
        assertEquals(categoria.getQuantidadeDeLeitos(), categoriaSalva.getQuantidadeDeLeitos());

    }

    @Test
    void deveriaExecutarValidationsAoCadastrar() {

        CadastroCategoriaDeUhDTO dto = new CadastroCategoriaDeUhDTO("Nome", 2);
        validationsList.add(validation1);
        validationsList.add(validation2);

        categoriaService.cadastrar(dto);

        BDDMockito.then(validation1).should().validate(dto);
        BDDMockito.then(validation2).should().validate(dto);

    }

    @Test
    void deveriaCriarUriAoCadastrar() {

        CadastroCategoriaDeUhDTO dto = new CadastroCategoriaDeUhDTO("Nome", 2);
        CategoriaDeUh categoria = new CategoriaDeUh(dto);
        categoria.setId(1L);

        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");

        URI uri = categoriaService.criarUri(categoria, uriBuilder);

        assertEquals("http://localhost:8080/categorias-de-uh/1", uri.toString());
    }

}
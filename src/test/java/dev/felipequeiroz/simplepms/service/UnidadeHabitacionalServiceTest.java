package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import dev.felipequeiroz.simplepms.domain.Cliente;
import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import dev.felipequeiroz.simplepms.dto.AtualizacaoClienteDTO;
import dev.felipequeiroz.simplepms.dto.AtualizacaoUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.dto.CadastroClienteDTO;
import dev.felipequeiroz.simplepms.dto.CadastroUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.repository.CategoriaDeUhRepository;
import dev.felipequeiroz.simplepms.repository.UnidadeHabitacionalRepository;
import dev.felipequeiroz.simplepms.validations.unidadeHabitacional.CadastrarUnidadeHabitacionalValidations;
import jakarta.persistence.EntityNotFoundException;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UnidadeHabitacionalServiceTest {

    @Mock
    private UnidadeHabitacionalRepository uhRepository;
    @Mock
    private CategoriaDeUhRepository categoriaRepository;
    @Spy
    private UnidadeHabitacional uh = new UnidadeHabitacional();
    @Spy
    private CategoriaDeUh categoria = new CategoriaDeUh();
    @Spy
    private List<CadastrarUnidadeHabitacionalValidations> validationsList = new ArrayList<>();
    @Mock
    private CadastrarUnidadeHabitacionalValidations validation1;
    @Mock
    private CadastrarUnidadeHabitacionalValidations validation2;
    @Captor
    private ArgumentCaptor<UnidadeHabitacional> uhCaptor;
    @Mock
    private UriComponentsBuilder uriBuilder;
    @InjectMocks
    private UnidadeHabitacionalService uhService;

    @Test
    void deveriaSalvarClienteAoCadastrar() {

        CadastroUnidadeHabitacionalDTO dto = new CadastroUnidadeHabitacionalDTO("Uh", 1L);
        CategoriaDeUh categoriaDeUh = new CategoriaDeUh(1L, "Categoria", 2, true);
        UnidadeHabitacional uh = new UnidadeHabitacional(1L, "Uh", categoriaDeUh, true);
        BDDMockito.given(categoriaRepository.getReferenceById(dto.idCategoriaDeUh())).willReturn(categoriaDeUh);

        uhService.cadastrar(dto);

        BDDMockito.then(uhRepository).should().save(uhCaptor.capture());
        UnidadeHabitacional uhSalva = uhCaptor.getValue();

        assertEquals(uh.getNomeUh(), uhSalva.getNomeUh());
        assertEquals(uh.getCategoriaDeUh(), uhSalva.getCategoriaDeUh());

    }

    @Test
    void deveriaExecutarValidationsAoCadastrar() {

        CadastroUnidadeHabitacionalDTO dto = new CadastroUnidadeHabitacionalDTO("Uh", 1L);
        validationsList.add(validation1);
        validationsList.add(validation2);

        uhService.cadastrar(dto);

        BDDMockito.then(validation1).should().validate(dto);
        BDDMockito.then(validation2).should().validate(dto);

    }

    @Test
    void deveriaCriarUriAoCadastrar() {

        CadastroUnidadeHabitacionalDTO dto = new CadastroUnidadeHabitacionalDTO("Uh", 1L);
        CategoriaDeUh categoriaDeUh = new CategoriaDeUh(1L, "Categoria", 2, true);
        UnidadeHabitacional uh = new UnidadeHabitacional(1L, "Uh", categoriaDeUh, true);

        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");

        URI uri = uhService.criarUri(uh, uriBuilder);

        assertEquals("http://localhost:8080/unidades-habitacionais/1", uri.toString());
    }

    @Test
    @DisplayName("Deveria atualizar os dados da uh ao atualizar com dados nao nulos")
    void atualizarComDadosNaoNulos() {

        AtualizacaoUnidadeHabitacionalDTO atualizacaoUh = new AtualizacaoUnidadeHabitacionalDTO(1L, "Novo nome", 2L);
        CategoriaDeUh categoriaNova = new CategoriaDeUh(2L, "Categoria", 2, true);
        UnidadeHabitacional uh = new UnidadeHabitacional(1L, "Nome atual", categoria, true);
        BDDMockito.given(categoriaRepository.getReferenceById(atualizacaoUh.idCategoriaDeUh())).willReturn(categoriaNova);
        BDDMockito.given(uhRepository.getReferenceById(atualizacaoUh.id())).willReturn(uh);

        UnidadeHabitacional uhAtualizada = uhService.atualizar(atualizacaoUh);


        assertEquals(atualizacaoUh.id(), uhAtualizada.getId());
        assertEquals(atualizacaoUh.nomeUh(), uhAtualizada.getNomeUh());
        assertEquals(atualizacaoUh.idCategoriaDeUh(), uhAtualizada.getCategoriaDeUh().getId());

    }

    @Test
    @DisplayName("Deveria manter os dados atuais da uh ao atualizar com dados nulos")
    void atualizarComDadosNulos() {

        AtualizacaoUnidadeHabitacionalDTO atualizacaoUh = new AtualizacaoUnidadeHabitacionalDTO(1L, null, null);
        UnidadeHabitacional uh = new UnidadeHabitacional(1L, "Nome atual", categoria, true);
        BDDMockito.given(uhRepository.getReferenceById(atualizacaoUh.id())).willReturn(uh);

        UnidadeHabitacional uhAtualizada = uhService.atualizar(atualizacaoUh);

        assertEquals(uh.getId(), uhAtualizada.getId());
        assertEquals(uh.getNomeUh(), uhAtualizada.getNomeUh());
        assertEquals(uh.getCategoriaDeUh().getId(), uhAtualizada.getCategoriaDeUh().getId());

    }

}
package dev.felipequeiroz.simplepms.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import dev.felipequeiroz.simplepms.domain.Cliente;
import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import dev.felipequeiroz.simplepms.dto.*;
import dev.felipequeiroz.simplepms.repository.CategoriaDeUhRepository;
import dev.felipequeiroz.simplepms.repository.UnidadeHabitacionalRepository;
import dev.felipequeiroz.simplepms.service.UnidadeHabitacionalService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class UnidadeHabitacionalControllerTest {

    @Autowired
    private JacksonTester<CadastroUnidadeHabitacionalDTO> jsonCadastroDto;
    @Autowired
    private JacksonTester<AtualizacaoUnidadeHabitacionalDTO> jsonAtualizacaoDto;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UnidadeHabitacionalService service;
    @MockBean
    private UnidadeHabitacionalRepository uhRepository;
    @MockBean
    private CategoriaDeUhRepository categoriaRepository;

    @Test
    @DisplayName("Deveria devolver codigo 400 para solicitacoes de cadastro com erros de validacoes")
    @WithMockUser
    void cadastroComErrosDeValidacoes() throws Exception {

        String json = "{}";

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/unidades-habitacionais")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("Deveria devolver codigo 201 para solicitacoes de cadastro sem erros de validacoes")
    @WithMockUser
    void cadastroSemErrosDeValidacoes() throws Exception {

        CadastroUnidadeHabitacionalDTO dto = new CadastroUnidadeHabitacionalDTO("Nome", 1L);
        CategoriaDeUh categoria = new CategoriaDeUh(1L, "Categoria", 2, true);
        UnidadeHabitacional uh = new UnidadeHabitacional(1L, dto.nomeUh(), categoria, true);

        BDDMockito.given(categoriaRepository.getReferenceById(dto.idCategoriaDeUh())).willReturn(categoria);
        BDDMockito.given(service.cadastrar(dto)).willReturn(uh);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/unidades-habitacionais")
                        .content(jsonCadastroDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(201, response.getStatus());

    }

    @Test
    @DisplayName("Deveria devolver codigo 200 para solicitacoes de atualizacao de cadastro enviando um id valido")
    @WithMockUser
    void atualizarComIdValido() throws Exception {

        AtualizacaoUnidadeHabitacionalDTO atualizacaoUh = new AtualizacaoUnidadeHabitacionalDTO(1L, "Novo nome", 1L);
        CategoriaDeUh categoria = new CategoriaDeUh(1L, "Categoria", 2, true);
        UnidadeHabitacional uh = new UnidadeHabitacional(1L, "Nome atual", categoria, true);
        BDDMockito.given(categoriaRepository.getReferenceById(atualizacaoUh.idCategoriaDeUh())).willReturn(categoria);
        BDDMockito.given(service.atualizar(atualizacaoUh)).willReturn(uh);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.put("/unidades-habitacionais")
                        .content(jsonAtualizacaoDto.write(atualizacaoUh).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Deveria devolver codigo 404 para solicitacoes de atualizacao de cadastro enviando um id invalido")
    @WithMockUser
    void atualizarComIdInvalido() throws Exception {


        AtualizacaoUnidadeHabitacionalDTO atualizacaoUh = new AtualizacaoUnidadeHabitacionalDTO(1L, "Novo nome", 1L);
        CategoriaDeUh categoria = new CategoriaDeUh(1L, "Categoria", 2, true);
        UnidadeHabitacional uh = new UnidadeHabitacional(1L, "Nome atual", categoria, true);
        BDDMockito.given(categoriaRepository.getReferenceById(atualizacaoUh.idCategoriaDeUh())).willReturn(categoria);
        BDDMockito.given(service.atualizar(atualizacaoUh)).willThrow(EntityNotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.put("/unidades-habitacionais")
                        .content(jsonAtualizacaoDto.write(atualizacaoUh).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        assertEquals(404, response.getStatus());

    }

    @Test
    @DisplayName("Deveria retornar codigo 204 ao excluir um cadastro ativo com id valido")
    @WithMockUser
    void excluirUhAtivaComIdValido() throws Exception {

        BDDMockito.willAnswer(invocation -> null).given(service).excluir(any(Long.class));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/unidades-habitacionais/1")
        ).andReturn().getResponse();

        assertEquals(204, response.getStatus());

    }

    @Test
    @DisplayName("Deveria retornar codigo 404 ao excluir um cadastro com id invalido")
    @WithMockUser
    void excluirUhAtivaComIdInvalido() throws Exception {

        BDDMockito.willThrow(EntityNotFoundException.class).given(service).excluir(any(Long.class));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/unidades-habitacionais/1")
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());

    }

    @Test
    @DisplayName("Deveria retornar codigo 400 ao excluir um cadastro ja inativo com id valido")
    @WithMockUser
    void excluirUhInativaComIdvalido() throws Exception {

        BDDMockito.willThrow(IllegalStateException.class).given(service).excluir(any(Long.class));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/unidades-habitacionais/1")
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("Deveria retornar codigo 200 e retornar as UHs ativas ao listar")
    @WithMockUser
    void listarUhs() throws Exception {

        CategoriaDeUh categoria = new CategoriaDeUh(1L, "Categoria", 2, true);
        UnidadeHabitacional uh = new UnidadeHabitacional(1L, "NomeUh", categoria, true);
        BDDMockito.given(categoriaRepository.getReferenceById(anyLong())).willReturn(categoria);
        List<UnidadeHabitacional> uhs = new ArrayList<>();
        uhs.add(uh);
        List<DetalhamentoUnidadeHabitacionalDTO> listaDetalhamentoUhDTO = uhs.stream().map(DetalhamentoUnidadeHabitacionalDTO::new).toList();

        when(uhRepository.findAllByAtivoTrue()).thenReturn(uhs);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/unidades-habitacionais")
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertEquals(listaDetalhamentoUhDTO, objectMapper.readValue(response.getContentAsString(), new TypeReference<List<DetalhamentoUnidadeHabitacionalDTO>>() {}));

    }

    @Test
    @DisplayName("Deveria retornar codigo 200 e retornar dados da UH ao detalhar")
    @WithMockUser
    void detalharUh() throws Exception {

        CategoriaDeUh categoria = new CategoriaDeUh(1L, "Categoria", 2, true);
        UnidadeHabitacional uh = new UnidadeHabitacional(1L, "NomeUh", categoria, true);
        BDDMockito.given(categoriaRepository.getReferenceById(anyLong())).willReturn(categoria);
        DetalhamentoUnidadeHabitacionalDTO detalhamentoUHDTO = new DetalhamentoUnidadeHabitacionalDTO(uh);

        when(uhRepository.getReferenceById(1L)).thenReturn(uh);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/unidades-habitacionais/1")
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertEquals(detalhamentoUHDTO, objectMapper.readValue(response.getContentAsString(), new TypeReference<DetalhamentoUnidadeHabitacionalDTO>() {}));

    }

}
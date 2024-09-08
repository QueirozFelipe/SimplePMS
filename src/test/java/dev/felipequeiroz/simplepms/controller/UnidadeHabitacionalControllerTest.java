package dev.felipequeiroz.simplepms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import dev.felipequeiroz.simplepms.domain.Cliente;
import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import dev.felipequeiroz.simplepms.dto.CadastroClienteDTO;
import dev.felipequeiroz.simplepms.dto.CadastroUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.repository.CategoriaDeUhRepository;
import dev.felipequeiroz.simplepms.repository.UnidadeHabitacionalRepository;
import dev.felipequeiroz.simplepms.service.UnidadeHabitacionalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class UnidadeHabitacionalControllerTest {

    @Autowired
    private JacksonTester<CadastroUnidadeHabitacionalDTO> jsonCadastroDto;
//    @Autowired
//    private JacksonTester<AtualizacaoClienteDTO> jsonAtualizacaoDto;
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

}
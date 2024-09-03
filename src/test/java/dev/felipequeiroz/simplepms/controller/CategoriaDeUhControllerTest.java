package dev.felipequeiroz.simplepms.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import dev.felipequeiroz.simplepms.dto.CadastroCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.dto.DetalhamentoCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.repository.CategoriaDeUhRepository;
import dev.felipequeiroz.simplepms.service.CategoriaDeUhService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CategoriaDeUhControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoriaDeUhService service;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JacksonTester<CadastroCategoriaDeUhDTO> jsonCadastroDto;

    @Test
    @DisplayName("Deveria retornar codigo 400 para solicitacoes de cadastro com erros de validacoes")
    @WithMockUser
    void cadastroComErrosDeValidacoes() throws Exception {

        String json = "{}";

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/categorias-de-uh")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("Deveria retornar codigo 200 e dados da categoria de uh ao cadastrar sem erros de validacoes")
    @WithMockUser
    void cadastrarSemErrosDeValidacoes() throws Exception {

        var cadastroCategoriaDeUhDTO = new CadastroCategoriaDeUhDTO("Nome", 2);
        CategoriaDeUh categoria = new CategoriaDeUh();
        categoria.setId(1L);
        categoria.setNomeCategoria("Nome");
        categoria.setQuantidadeDeLeitos(2);
        var categoriaDTO = new DetalhamentoCategoriaDeUhDTO(categoria);

        when(service.cadastrar(cadastroCategoriaDeUhDTO)).thenReturn(categoria);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/categorias-de-uh")
                        .content(jsonCadastroDto.write(cadastroCategoriaDeUhDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(201, response.getStatus());
        assertEquals(categoriaDTO, objectMapper.readValue(response.getContentAsString(), new TypeReference<DetalhamentoCategoriaDeUhDTO>() {}));

    }

}
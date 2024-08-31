package dev.felipequeiroz.simplepms.controller;

import dev.felipequeiroz.simplepms.domain.Cliente;
import dev.felipequeiroz.simplepms.dto.AtualizacaoClienteDTO;
import dev.felipequeiroz.simplepms.dto.CadastroClienteDTO;
import dev.felipequeiroz.simplepms.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
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

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ClienteControllerTest {

    @Autowired
    private JacksonTester<CadastroClienteDTO> jsonCadastroDto;
    @Autowired
    private JacksonTester<AtualizacaoClienteDTO> jsonAtualizacaoDto;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClienteService clienteService;

    @Test
    @DisplayName("Deveria devolver codigo 400 para solicitacoes de cadastro com erros de validacoes")
    @WithMockUser
    void cadastroComErrosDeValidacoes() throws Exception {

        String json = "{}";

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/clientes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("Deveria devolver codigo 201 para solicitacoes de cadastro sem erros de validacoes")
    @WithMockUser
    void cadastroSemErrosDeValidacoes() throws Exception {

        CadastroClienteDTO dto = new CadastroClienteDTO("Nome", "123", LocalDate.of(1991,01,01), "test@test.com", "1111111111");
        BDDMockito.given(clienteService.cadastrar(dto)).willReturn(new Cliente(dto));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/clientes")
                        .content(jsonCadastroDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(201, response.getStatus());

    }

    @Test
    @DisplayName("Deveria devolver codigo 200 para solicitacoes de atualizacao de cadastro enviando um id valido")
    @WithMockUser
    void atualizarComIdValido() throws Exception {

        AtualizacaoClienteDTO atualizacaoClienteDTO = new AtualizacaoClienteDTO(1l, "Novo nome", null, null, null);
        Cliente cliente = new Cliente(new CadastroClienteDTO("Nome", "123", LocalDate.of(1991,01,01), "test@test.com", "1111111111"));
        BDDMockito.given(clienteService.atualizar(atualizacaoClienteDTO)).willReturn(cliente);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.put("/clientes")
                        .content(jsonAtualizacaoDto.write(atualizacaoClienteDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Deveria devolver codigo 404 para solicitacoes de atualizacao de cadastro enviando um id invalido")
    @WithMockUser
    void atualizarComIdInvalido() throws Exception {

        AtualizacaoClienteDTO atualizacaoClienteDTO = new AtualizacaoClienteDTO(1l, "Novo nome", null, null, null);
        Cliente cliente = new Cliente(new CadastroClienteDTO("Nome", "123", LocalDate.of(1991,01,01), "test@test.com", "1111111111"));
        BDDMockito.given(clienteService.atualizar(atualizacaoClienteDTO)).willThrow(EntityNotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.put("/clientes")
                        .content(jsonAtualizacaoDto.write(atualizacaoClienteDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }




}
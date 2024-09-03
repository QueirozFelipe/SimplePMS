package dev.felipequeiroz.simplepms.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.felipequeiroz.simplepms.domain.Cliente;
import dev.felipequeiroz.simplepms.dto.AtualizacaoClienteDTO;
import dev.felipequeiroz.simplepms.dto.CadastroClienteDTO;
import dev.felipequeiroz.simplepms.dto.DetalhamentoClienteDTO;
import dev.felipequeiroz.simplepms.repository.ClienteRepository;
import dev.felipequeiroz.simplepms.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ClienteControllerTest {

    @Autowired
    private JacksonTester<CadastroClienteDTO> jsonCadastroDto;
    @Autowired
    private JacksonTester<AtualizacaoClienteDTO> jsonAtualizacaoDto;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClienteService clienteService;
    @MockBean
    private ClienteRepository clienteRepository;

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

    @Test
    @DisplayName("Deveria retornar codigo 204 ao excluir um cadastro ativo com id valido")
    @WithMockUser
    void excluirClienteAtivoComIdValido() throws Exception {

        BDDMockito.willAnswer(invocation -> null).given(clienteService).excluir(any(Long.class));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/clientes/1")
        ).andReturn().getResponse();

        assertEquals(204, response.getStatus());

    }

    @Test
    @DisplayName("Deveria retornar codigo 404 ao excluir um cadastro com id invalido")
    @WithMockUser
    void excluirClienteAtivoComIdInvalido() throws Exception {

        BDDMockito.willThrow(EntityNotFoundException.class).given(clienteService).excluir(any(Long.class));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/clientes/1")
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());

    }

    @Test
    @DisplayName("Deveria retornar codigo 400 ao excluir um cadastro ja inativo com id valido")
    @WithMockUser
    void excluirClienteInativoComIdvalido() throws Exception {

        BDDMockito.willThrow(IllegalStateException.class).given(clienteService).excluir(any(Long.class));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/clientes/1")
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("Deveria retornar codigo 200 e retornar os clientes ativos ao listar")
    @WithMockUser
    void listarClientes() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNomeCompleto("Cliente");
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);
        List<DetalhamentoClienteDTO> listaDetalhamentoClienteDto = clientes.stream().map(DetalhamentoClienteDTO::new).toList();

        when(clienteRepository.findAllByAtivoTrue()).thenReturn(clientes);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/clientes")
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertEquals(listaDetalhamentoClienteDto, objectMapper.readValue(response.getContentAsString(), new TypeReference<List<DetalhamentoClienteDTO>>() {}));

    }

    @Test
    @DisplayName("Deveria retornar codigo 200 e retornar dados do cliente ao detalhar")
    @WithMockUser
    void detalharCliente() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNomeCompleto("Cliente");
        var detalhamentoClienteDTO = new DetalhamentoClienteDTO(cliente);

        when(clienteRepository.getReferenceById(1L)).thenReturn(cliente);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/clientes/1")
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertEquals(detalhamentoClienteDTO, objectMapper.readValue(response.getContentAsString(), new TypeReference<DetalhamentoClienteDTO>() {}));

    }


}
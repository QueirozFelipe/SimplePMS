package dev.felipequeiroz.simplepms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.felipequeiroz.simplepms.domain.*;
import dev.felipequeiroz.simplepms.dto.tarifa.AtualizacaoTarifaDTO;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDTO;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDetalhamentoDTO;
import dev.felipequeiroz.simplepms.dto.unidadeHabitacional.AtualizacaoUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.repository.TarifaRepository;
import dev.felipequeiroz.simplepms.service.TarifaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TarifaControllerTest {

    @Autowired
    private JacksonTester<CadastroTarifaDTO> jsonCadastroTarifaDto;
    @Autowired
    private JacksonTester<AtualizacaoTarifaDTO> jsonAtualizacaoTarifaDto;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TarifaService service;
    @MockBean
    private TarifaRepository tarifaRepository;
    @MockBean
    private CadastroTarifaDetalhamentoDTO cadastroTarifaDetalhamentoDTO;
    @Spy
    private TarifaDetalhamento tarifaDetalhamento = new TarifaDetalhamento();

    @Test
    @DisplayName("Deveria devolver codigo 400 para solicitacoes de cadastro com erros de validacoes")
    @WithMockUser
    void cadastroComErrosDeValidacoes() throws Exception {

        String json = "{}";

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/tarifas")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("Deveria devolver codigo 201 para solicitacoes de cadastro sem erros de validacoes")
    @WithMockUser
    void cadastroSemErrosDeValidacoes() throws Exception {

        CadastroTarifaDTO dto = new CadastroTarifaDTO("Tarifa", new BigDecimal("100.00"),
                List.of(new CadastroTarifaDetalhamentoDTO(ClassificacaoHospede.ADULTO, new BigInteger("50"))));

        Tarifa tarifa = new Tarifa(dto);
        tarifa.setId(1L);

        BDDMockito.given(service.cadastrar(dto)).willReturn(tarifa);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/tarifas")
                        .content(jsonCadastroTarifaDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(201, response.getStatus());
    }

    @Test
    @DisplayName("Deveria devolver codigo 200 para solicitacoes de atualizacao de tarifa enviando um id valido")
    @WithMockUser
    void atualizarComIdValido() throws Exception {

        AtualizacaoTarifaDTO  atualizacaoTarifaDTO = new AtualizacaoTarifaDTO(1L, "Novo nome", null, null);
        Tarifa tarifa = new Tarifa(1L, "Nome anterior", new BigDecimal("100.0"), List.of(tarifaDetalhamento), true);

        BDDMockito.given(service.atualizar(atualizacaoTarifaDTO)).willReturn(tarifa);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.put("/tarifas")
                        .content(jsonAtualizacaoTarifaDto.write(atualizacaoTarifaDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Deveria devolver codigo 404 para solicitacoes de atualizacao de tarifa enviando um id invalido")
    @WithMockUser
    void atualizarComIdInvalido() throws Exception {

        AtualizacaoTarifaDTO  atualizacaoTarifaDTO = new AtualizacaoTarifaDTO(1L, "Novo nome", null, null);
        BDDMockito.given(service.atualizar(atualizacaoTarifaDTO)).willThrow(EntityNotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.put("/tarifas")
                        .content(jsonAtualizacaoTarifaDto.write(atualizacaoTarifaDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        assertEquals(404, response.getStatus());

    }

    @Test
    @DisplayName("Deveria retornar codigo 204 ao excluir um cadastro ativo com id valido")
    @WithMockUser
    void excluirTarifaAtivaComIdValido() throws Exception {

        BDDMockito.willAnswer(invocation -> null).given(service).excluir(any(Long.class));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/tarifas/1")
        ).andReturn().getResponse();

        assertEquals(204, response.getStatus());

    }

    @Test
    @DisplayName("Deveria retornar codigo 404 ao excluir um cadastro com id invalido")
    @WithMockUser
    void excluirTarifaAtivaComIdInvalido() throws Exception {

        BDDMockito.willThrow(EntityNotFoundException.class).given(service).excluir(any(Long.class));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/tarifas/1")
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());

    }

    @Test
    @DisplayName("Deveria retornar codigo 400 ao excluir um cadastro ja inativo com id valido")
    @WithMockUser
    void excluirTarifaInativaComIdvalido() throws Exception {

        BDDMockito.willThrow(IllegalStateException.class).given(service).excluir(any(Long.class));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/tarifas/1")
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());

    }



}
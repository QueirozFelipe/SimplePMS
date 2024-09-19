package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.Cliente;
import dev.felipequeiroz.simplepms.dto.cliente.AtualizacaoClienteDTO;
import dev.felipequeiroz.simplepms.dto.cliente.CadastroClienteDTO;
import dev.felipequeiroz.simplepms.repository.ClienteRepository;
import dev.felipequeiroz.simplepms.validations.cliente.CadastrarClienteValidations;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Spy
    private Cliente cliente = new Cliente();
    @Spy
    private List<CadastrarClienteValidations> validationsList = new ArrayList<>();
    @Mock
    private CadastrarClienteValidations validation1;
    @Mock
    private CadastrarClienteValidations validation2;
    @Captor
    private ArgumentCaptor<Cliente> clienteCaptor;
    @Mock
    private UriComponentsBuilder uriBuilder;
    @InjectMocks
    ClienteService clienteService;

    @Test
    void deveriaSalvarClienteAoCadastrar() {

        CadastroClienteDTO dto = new CadastroClienteDTO("Nome", "123", LocalDate.of(1991,01,01), "test@test.com", "1111111111");
        Cliente cliente = new Cliente(dto);

        clienteService.cadastrar(dto);

        BDDMockito.then(clienteRepository).should().save(clienteCaptor.capture());
        Cliente clienteSalvo = clienteCaptor.getValue();
        assertEquals(cliente.getNomeCompleto(), clienteSalvo.getNomeCompleto());
        assertEquals(cliente.getDocumento(), clienteSalvo.getDocumento());
        assertEquals(cliente.getDataDeNascimento(), clienteSalvo.getDataDeNascimento());
        assertEquals(cliente.getEmail(), clienteSalvo.getEmail());
        assertEquals(cliente.getTelefone(), clienteSalvo.getTelefone());
    }

    @Test
    void deveriaExecutarValidationsAoCadastrar() {

        CadastroClienteDTO dto = new CadastroClienteDTO("Nome", "123", LocalDate.of(1991,01,01), "test@test.com", "1111111111");
        validationsList.add(validation1);
        validationsList.add(validation2);

        clienteService.cadastrar(dto);

        BDDMockito.then(validation1).should().validate(dto);
        BDDMockito.then(validation2).should().validate(dto);

    }

    @Test
    void deveriaCriarUriAoCadastrar() {

        CadastroClienteDTO dto = new CadastroClienteDTO("Nome", "123", LocalDate.of(1991,01,01), "test@test.com", "1111111111");
        Cliente cliente = new Cliente(dto);
        cliente.setId(1L);

        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");

        URI uri = clienteService.criarUri(cliente, uriBuilder);

        assertEquals("http://localhost:8080/clientes/1", uri.toString());
    }

    @Test
    @DisplayName("Deveria atualizar os dados do cliente ao atualizar com dados nao nulos")
    void atualizarComDadosNaoNulos() {

        AtualizacaoClienteDTO dto = new AtualizacaoClienteDTO(1l, "Novo nome", LocalDate.of(2000, 01, 01), "novo@email.com", "2222222222");
        Cliente cliente = new Cliente(new CadastroClienteDTO("Nome", "123", LocalDate.of(1991,01,01), "test@test.com", "1111111111"));
        cliente.setId(1L);
        BDDMockito.given(clienteRepository.getReferenceById(dto.id())).willReturn(cliente);

        Cliente clienteAtualizado = clienteService.atualizar(dto);

        assertEquals(dto.id(), clienteAtualizado.getId());
        assertEquals(dto.nomeCompleto(), clienteAtualizado.getNomeCompleto());
        assertEquals(dto.dataDeNascimento(), clienteAtualizado.getDataDeNascimento());
        assertEquals(dto.email(), clienteAtualizado.getEmail());
        assertEquals(dto.telefone(), clienteAtualizado.getTelefone());

    }

    @Test
    @DisplayName("Deveria manter os dados atuais do cliente ao atualizar com dados nulos")
    void atualizarComDadosNulos() {

        AtualizacaoClienteDTO dto = new AtualizacaoClienteDTO(1l, null, null, null, null);
        Cliente cliente = new Cliente(new CadastroClienteDTO("Nome", "123", LocalDate.of(1991,01,01), "test@test.com", "1111111111"));
        cliente.setId(1L);
        BDDMockito.given(clienteRepository.getReferenceById(dto.id())).willReturn(cliente);

        Cliente clienteAtualizado = clienteService.atualizar(dto);

        assertEquals(cliente.getId(), clienteAtualizado.getId());
        assertEquals(cliente.getNomeCompleto(), clienteAtualizado.getNomeCompleto());
        assertEquals(cliente.getDataDeNascimento(), clienteAtualizado.getDataDeNascimento());
        assertEquals(cliente.getEmail(), clienteAtualizado.getEmail());
        assertEquals(cliente.getTelefone(), clienteAtualizado.getTelefone());

    }

    @Test
    @DisplayName("Deveria alterar o parametro ativo para false ao receber um id valido e cliente esta ativo")
    void excluirComIdValidoEClienteAtivo() {

        cliente.setId(1l);
        cliente.setAtivo(true);
        BDDMockito.given(clienteRepository.getReferenceById(1L)).willReturn(cliente);

        clienteService.excluir(1L);

        assertEquals(false, cliente.getAtivo());

    }

    @Test
    @DisplayName("Deveria lancar exception ao receber um id valido e cliente esta inativo")
    void excluirComIdValidoEClienteInativo() {

        cliente.setId(1l);
        cliente.setAtivo(false);
        BDDMockito.given(clienteRepository.getReferenceById(1L)).willReturn(cliente);

        assertThrows(IllegalStateException.class, () -> clienteService.excluir(1L));

    }





}
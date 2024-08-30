package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.Cliente;
import dev.felipequeiroz.simplepms.dto.CadastroClienteDTO;
import dev.felipequeiroz.simplepms.repository.ClienteRepository;
import dev.felipequeiroz.simplepms.validations.cliente.CadastrarClienteValidations;
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
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
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


}
package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.Cliente;
import dev.felipequeiroz.simplepms.dto.CadastroClienteDTO;
import dev.felipequeiroz.simplepms.repository.ClienteRepository;
import dev.felipequeiroz.simplepms.validations.cliente.CadastrarClienteValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private List<CadastrarClienteValidations> validationsList;

    public Cliente cadastrar(CadastroClienteDTO dto) {

        validationsList.forEach(v -> v.validate(dto));

        Cliente cliente = new Cliente(dto);
        return clienteRepository.save(cliente);
    }

    public URI criarUri(Cliente cliente, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("clientes/{id}").buildAndExpand(cliente.getId()).toUri();
    }
}

package dev.felipequeiroz.simplepms.controller;

import dev.felipequeiroz.simplepms.domain.Cliente;
import dev.felipequeiroz.simplepms.dto.cliente.AtualizacaoClienteDTO;
import dev.felipequeiroz.simplepms.dto.cliente.CadastroClienteDTO;
import dev.felipequeiroz.simplepms.dto.cliente.DetalhamentoClienteDTO;
import dev.felipequeiroz.simplepms.repository.ClienteRepository;
import dev.felipequeiroz.simplepms.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@SecurityRequirement(name = "bearer-key")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ClienteRepository clienteRepository;

    @Operation(summary = "Cadastra um novo cliente")
    @PostMapping
    @Transactional
    public ResponseEntity<DetalhamentoClienteDTO> cadastrar(@RequestBody @Valid CadastroClienteDTO dto, UriComponentsBuilder uriBuilder) {

        Cliente cliente = clienteService.cadastrar(dto);
        URI uri = clienteService.criarUri(cliente, uriBuilder);

        return ResponseEntity.created(uri).body(new DetalhamentoClienteDTO(cliente));
    }

    @Operation(summary = "Atualiza um cadastro de cliente")
    @PutMapping
    @Transactional
    public ResponseEntity<DetalhamentoClienteDTO> atualizar(@RequestBody @Valid AtualizacaoClienteDTO dto) {

        Cliente cliente = clienteService.atualizar(dto);

        return ResponseEntity.ok(new DetalhamentoClienteDTO(cliente));

    }

    @Operation(summary = "Marca como inativo um cadastro de cliente")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {

        clienteService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista todos os clientes ativos")
    @GetMapping
    public ResponseEntity<List<DetalhamentoClienteDTO>> listar() {

        var lista = clienteRepository.findAllByAtivoTrue().stream().map(DetalhamentoClienteDTO::new).toList();
        return ResponseEntity.ok(lista);

    }

    @Operation(summary = "Detalha um cliente através do Id")
    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoClienteDTO> detalhar(@PathVariable Long id) {

        var cliente = new DetalhamentoClienteDTO(clienteRepository.getReferenceById(id));
        return ResponseEntity.ok(cliente);

    }


}

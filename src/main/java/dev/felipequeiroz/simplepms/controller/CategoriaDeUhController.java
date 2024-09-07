package dev.felipequeiroz.simplepms.controller;

import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import dev.felipequeiroz.simplepms.dto.AtualizacaoCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.dto.CadastroCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.dto.DetalhamentoCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.repository.CategoriaDeUhRepository;
import dev.felipequeiroz.simplepms.service.CategoriaDeUhService;
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
@RequestMapping("/categorias-de-uh")
@SecurityRequirement(name = "bearer-key")
public class CategoriaDeUhController {

    @Autowired
    private CategoriaDeUhService service;

    @Autowired
    private CategoriaDeUhRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DetalhamentoCategoriaDeUhDTO> cadastrar(@RequestBody @Valid CadastroCategoriaDeUhDTO dto, UriComponentsBuilder uriBuilder) {

        CategoriaDeUh categoria = service.cadastrar(dto);
        URI uri = service.criarUri(categoria, uriBuilder);

        return ResponseEntity.created(uri).body(new DetalhamentoCategoriaDeUhDTO(categoria));

    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetalhamentoCategoriaDeUhDTO> atualizar(@RequestBody @Valid AtualizacaoCategoriaDeUhDTO dto) {

        CategoriaDeUh categoria = service.atualizar(dto);

        return ResponseEntity.ok(new DetalhamentoCategoriaDeUhDTO(categoria));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<DetalhamentoCategoriaDeUhDTO>> listar() {

        var lista = repository.findAllByAtivoTrue().stream().map(DetalhamentoCategoriaDeUhDTO::new).toList();
        return ResponseEntity.ok(lista);

    }

}

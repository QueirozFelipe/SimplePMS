package dev.felipequeiroz.simplepms.controller;

import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import dev.felipequeiroz.simplepms.dto.CadastroCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.dto.DetalhamentoCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.service.CategoriaDeUhService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categorias-de-uh")
@SecurityRequirement(name = "bearer-key")
public class CategoriaDeUhController {

    @Autowired
    private CategoriaDeUhService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DetalhamentoCategoriaDeUhDTO> cadastrar(@RequestBody @Valid CadastroCategoriaDeUhDTO dto, UriComponentsBuilder uriBuilder) {

        CategoriaDeUh categoria = service.cadastrar(dto);
        URI uri = service.criarUri(categoria, uriBuilder);

        return ResponseEntity.created(uri).body(new DetalhamentoCategoriaDeUhDTO(categoria));

    }

}

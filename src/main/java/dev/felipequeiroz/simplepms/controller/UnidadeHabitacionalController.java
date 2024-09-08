package dev.felipequeiroz.simplepms.controller;

import dev.felipequeiroz.simplepms.domain.Cliente;
import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import dev.felipequeiroz.simplepms.dto.*;
import dev.felipequeiroz.simplepms.service.UnidadeHabitacionalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/unidades-habitacionais")
@SecurityRequirement(name = "bearer-key")
public class UnidadeHabitacionalController {

    @Autowired
    private UnidadeHabitacionalService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroUnidadeHabitacionalDTO dto, UriComponentsBuilder uriBuilder) {

        UnidadeHabitacional uh = service.cadastrar(dto);
        URI uri = service.criarUri(uh, uriBuilder);

        return ResponseEntity.created(uri).body(new DetalhamentoUnidadeHabitacionalDTO(uh));

    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetalhamentoUnidadeHabitacionalDTO> atualizar(@RequestBody @Valid AtualizacaoUnidadeHabitacionalDTO dto) {

        UnidadeHabitacional uh = service.atualizar(dto);

        return ResponseEntity.ok(new DetalhamentoUnidadeHabitacionalDTO(uh));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }



}

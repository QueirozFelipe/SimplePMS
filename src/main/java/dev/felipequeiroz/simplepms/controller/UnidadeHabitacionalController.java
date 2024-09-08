package dev.felipequeiroz.simplepms.controller;

import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import dev.felipequeiroz.simplepms.dto.CadastroUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.dto.DetalhamentoUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.service.UnidadeHabitacionalService;
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
@RequestMapping("/unidades-habitacionais")
@SecurityRequirement(name = "bearer-key")
public class UnidadeHabitacionalController {

    @Autowired
    private UnidadeHabitacionalService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastra(@RequestBody @Valid CadastroUnidadeHabitacionalDTO dto, UriComponentsBuilder uriBuilder) {

        UnidadeHabitacional uh = service.cadastrar(dto);
        URI uri = service.criarUri(uh, uriBuilder);

        return ResponseEntity.created(uri).body(new DetalhamentoUnidadeHabitacionalDTO(uh));

    }

}

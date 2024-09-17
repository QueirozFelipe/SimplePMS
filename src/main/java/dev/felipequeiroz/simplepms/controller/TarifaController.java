package dev.felipequeiroz.simplepms.controller;

import dev.felipequeiroz.simplepms.domain.Tarifa;
import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import dev.felipequeiroz.simplepms.dto.CadastroTarifaDTO;
import dev.felipequeiroz.simplepms.dto.CadastroUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.dto.DetalhamentoTarifaDTO;
import dev.felipequeiroz.simplepms.dto.DetalhamentoUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.service.TarifaService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/tarifas")
@SecurityRequirement(name = "bearer-key")
public class TarifaController {

        @Autowired
        private TarifaService service;

        @Operation(summary = "Cadastra uma nova tarifa")
        @PostMapping
        @Transactional
        public ResponseEntity cadastrar(@RequestBody @Valid CadastroTarifaDTO dto, UriComponentsBuilder uriBuilder) {

                Tarifa tarifa = service.cadastrar(dto);
                URI uri = service.criarUri(tarifa, uriBuilder);

                return ResponseEntity.created(uri).body(new DetalhamentoTarifaDTO(tarifa));

        }

}

package dev.felipequeiroz.simplepms.controller;

import dev.felipequeiroz.simplepms.domain.Tarifa;
import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import dev.felipequeiroz.simplepms.dto.tarifa.AtualizacaoTarifaDTO;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDTO;
import dev.felipequeiroz.simplepms.dto.tarifa.DetalhamentoTarifaDTO;
import dev.felipequeiroz.simplepms.dto.unidadeHabitacional.AtualizacaoUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.dto.unidadeHabitacional.DetalhamentoUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.repository.TarifaRepository;
import dev.felipequeiroz.simplepms.service.TarifaService;
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
@RequestMapping("/tarifas")
@SecurityRequirement(name = "bearer-key")
public class TarifaController {

        @Autowired
        private TarifaService service;

        @Autowired
        private TarifaRepository repository;

        @Operation(summary = "Cadastra uma nova tarifa")
        @PostMapping
        @Transactional
        public ResponseEntity cadastrar(@RequestBody @Valid CadastroTarifaDTO dto, UriComponentsBuilder uriBuilder) {

                Tarifa tarifa = service.cadastrar(dto);
                URI uri = service.criarUri(tarifa, uriBuilder);

                return ResponseEntity.created(uri).body(new DetalhamentoTarifaDTO(tarifa));

        }

        @Operation(summary = "Atualiza um cadastro de tarifa")
        @PutMapping
        @Transactional
        public ResponseEntity<DetalhamentoTarifaDTO> atualizar(@RequestBody @Valid AtualizacaoTarifaDTO dto) {

                Tarifa tarifa = service.atualizar(dto);

                return ResponseEntity.ok(new DetalhamentoTarifaDTO(tarifa));

        }

        @Operation(summary = "Marca como inativa um cadastro de tarifa")
        @DeleteMapping("/{id}")
        @Transactional
        public ResponseEntity excluir(@PathVariable Long id) {

                service.excluir(id);

                return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Lista todas as tarifas ativas")
        @GetMapping
        public ResponseEntity<List<DetalhamentoTarifaDTO>> listar() {

                var lista = repository.findAllByAtivoTrue().stream().map(DetalhamentoTarifaDTO::new).toList();
                return ResponseEntity.ok(lista);

        }

        @Operation(summary = "Detalha uma tarifa através do Id")
        @GetMapping("/{id}")
        public ResponseEntity<DetalhamentoTarifaDTO> detalhar(@PathVariable Long id) {

                var tarifa = new DetalhamentoTarifaDTO(repository.getReferenceById(id));
                return ResponseEntity.ok(tarifa);

        }

}

package dev.felipequeiroz.simplepms.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tarifas")
@SecurityRequirement(name = "bearer-key")
public class TarifaController {



}

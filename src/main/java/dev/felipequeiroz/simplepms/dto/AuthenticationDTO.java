package dev.felipequeiroz.simplepms.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthenticationDTO(
        @Schema(example = "admin")
        String username,
        @Schema(example = "admin")
        String password) {
}

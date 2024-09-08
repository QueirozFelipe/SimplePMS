package dev.felipequeiroz.simplepms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CadastroClienteDTO(
        @NotBlank
        String nomeCompleto,
        @NotBlank
        String documento,
        @NotNull
        LocalDate dataDeNascimento,
        @Email
        String email,
        String telefone) {
}

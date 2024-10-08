package dev.felipequeiroz.simplepms.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AtualizacaoClienteDTO(
        @NotNull
        Long id,
        String nomeCompleto,
        LocalDate dataDeNascimento,
        String email,
        String telefone) {
}

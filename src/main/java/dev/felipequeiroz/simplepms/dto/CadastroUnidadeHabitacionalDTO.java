package dev.felipequeiroz.simplepms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastroUnidadeHabitacionalDTO(

        @Size(min = 2, message = "O nome da UH deve ter pelo menos 2 caracteres.")
        @NotBlank
        String nomeUh,
        @NotNull
        Long idCategoriaDeUh) {
}

package dev.felipequeiroz.simplepms.dto.categoriaDeUh;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CadastroCategoriaDeUhDTO(
        @NotBlank
        String nomeCategoria,

        @Min(value = 1, message = "Quantidade mínima é 1.")
        @Max(value = 100, message = "Quantidade máxima é 100.")
        Integer quantidadeDeLeitos) {
}

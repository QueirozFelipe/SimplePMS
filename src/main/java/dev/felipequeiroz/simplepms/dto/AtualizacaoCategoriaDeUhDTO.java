package dev.felipequeiroz.simplepms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AtualizacaoCategoriaDeUhDTO(
        @NotNull Long id,
        String nomeCategoria,
        @Min(value = 1, message = "Quantidade mínima é 1.")
        @Max(value = 100, message = "Quantidade máxima é 100.")
        Integer quantidadeDeLeitos) {
}

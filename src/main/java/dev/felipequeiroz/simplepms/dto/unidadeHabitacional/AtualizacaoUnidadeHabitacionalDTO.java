package dev.felipequeiroz.simplepms.dto.unidadeHabitacional;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AtualizacaoUnidadeHabitacionalDTO(

        @NotNull
        Long id,
        @Size(min = 2, message = "O nome da UH deve ter pelo menos 2 caracteres.")
        String nomeUh,
        @NotNull
        Long idCategoriaDeUh) {

}

package dev.felipequeiroz.simplepms.dto;

import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;

public record DetalhamentoUnidadeHabitacionalDTO(Long id, String nomeUh, DetalhamentoCategoriaDeUhDTO categoriaDeUh) {

    public DetalhamentoUnidadeHabitacionalDTO(UnidadeHabitacional uh) {
        this(uh.getId(), uh.getNomeUh(), new DetalhamentoCategoriaDeUhDTO(uh.getCategoriaDeUh()));
    }

}

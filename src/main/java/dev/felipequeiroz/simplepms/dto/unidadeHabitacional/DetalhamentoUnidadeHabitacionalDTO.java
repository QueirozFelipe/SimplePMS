package dev.felipequeiroz.simplepms.dto.unidadeHabitacional;

import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import dev.felipequeiroz.simplepms.dto.categoriaDeUh.DetalhamentoCategoriaDeUhDTO;

public record DetalhamentoUnidadeHabitacionalDTO(Long id, String nomeUh, DetalhamentoCategoriaDeUhDTO categoriaDeUh) {

    public DetalhamentoUnidadeHabitacionalDTO(UnidadeHabitacional uh) {
        this(uh.getId(), uh.getNomeUh(), new DetalhamentoCategoriaDeUhDTO(uh.getCategoriaDeUh()));
    }

}

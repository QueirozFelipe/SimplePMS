package dev.felipequeiroz.simplepms.dto;

import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;

public record DetalhamentoCategoriaDeUhDTO(Long id, String nomeCategoria, Integer quantidadeDeLeitos) {

    public DetalhamentoCategoriaDeUhDTO(CategoriaDeUh categoria) {
        this(categoria.getId(), categoria.getNomeCategoria(), categoria.getQuantidadeDeLeitos());
    }
}

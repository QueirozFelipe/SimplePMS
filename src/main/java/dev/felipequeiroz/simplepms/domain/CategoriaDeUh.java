package dev.felipequeiroz.simplepms.domain;

import dev.felipequeiroz.simplepms.dto.CadastroCategoriaDeUhDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias_de_uh")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CategoriaDeUh {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCategoria;
    private Integer quantidadeDeLeitos;
    private Boolean ativo = true;

    public CategoriaDeUh(CadastroCategoriaDeUhDTO dto) {
        this.nomeCategoria = dto.nomeCategoria();
        this.quantidadeDeLeitos = dto.quantidadeDeLeitos();
    }
}

package dev.felipequeiroz.simplepms.domain;

import dev.felipequeiroz.simplepms.dto.CadastroUnidadeHabitacionalDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "unidades_habitacionais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UnidadeHabitacional {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeUh;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_de_uh_id")
    private CategoriaDeUh categoriaDeUh;
    private Boolean ativo = true;

}

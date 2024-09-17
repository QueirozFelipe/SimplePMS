package dev.felipequeiroz.simplepms.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tarifas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Tarifa {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeTarifa;
    private BigDecimal valorBase;
    @OneToMany(mappedBy = "tarifa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TarifaDetalhamento> tarifaDetalhamentos;
    private Boolean ativo = true;

}

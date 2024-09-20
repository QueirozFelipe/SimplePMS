package dev.felipequeiroz.simplepms.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Table(name = "tarifa_detalhamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TarifaDetalhamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarifa_id")
    private Tarifa tarifa;
    @Enumerated(EnumType.STRING)
    private ClassificacaoHospede classificacaoHospede;
    private BigInteger valorHospedeAdicional;

}

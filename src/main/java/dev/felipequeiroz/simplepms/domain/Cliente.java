package dev.felipequeiroz.simplepms.domain;

import dev.felipequeiroz.simplepms.dto.CadastroClienteDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String documento;
    private LocalDate dataDeNascimento;
    private String email;
    private String telefone;
    private Boolean ativo = true;

    public Cliente(CadastroClienteDTO dto) {
        this.nomeCompleto = dto.nomeCompleto();
        this.documento = dto.documento();
        this.dataDeNascimento = dto.dataDeNascimento();
        this.email = dto.email();
        this.telefone = dto.telefone();
    }

}

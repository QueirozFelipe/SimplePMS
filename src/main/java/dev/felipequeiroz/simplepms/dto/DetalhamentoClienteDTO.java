package dev.felipequeiroz.simplepms.dto;

import dev.felipequeiroz.simplepms.domain.Cliente;

import java.time.LocalDate;

public record DetalhamentoClienteDTO(Long id, String nomeCompleto, String documento,
                                     LocalDate dataDeNascimento, String email, String telefone) {

    public DetalhamentoClienteDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getNomeCompleto(), cliente.getDocumento(),
                cliente.getDataDeNascimento(), cliente.getEmail(), cliente.getTelefone());
    }

}

package dev.felipequeiroz.simplepms.repository;

import dev.felipequeiroz.simplepms.domain.Cliente;
import dev.felipequeiroz.simplepms.dto.CadastroClienteDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Boolean existsByDocumento(String documento);

    Boolean existsByEmail(String email);

    Boolean existsByTelefone(String telefone);

}

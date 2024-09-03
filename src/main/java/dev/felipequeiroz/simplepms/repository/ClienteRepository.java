package dev.felipequeiroz.simplepms.repository;

import dev.felipequeiroz.simplepms.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Boolean existsByDocumento(String documento);

    Boolean existsByEmail(String email);

    Boolean existsByTelefone(String telefone);

    List<Cliente> findAllByAtivoTrue();
}

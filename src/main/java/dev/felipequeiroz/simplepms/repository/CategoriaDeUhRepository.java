package dev.felipequeiroz.simplepms.repository;

import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaDeUhRepository extends JpaRepository<CategoriaDeUh, Long> {

    boolean existsByNomeCategoriaIgnoreCase(String s);

    List<CategoriaDeUh> findAllByAtivoTrue();

}

package dev.felipequeiroz.simplepms.repository;

import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaDeUhRepository extends JpaRepository<CategoriaDeUh, Long> {

    boolean existsByNomeCategoriaIgnoreCase(String s);

}

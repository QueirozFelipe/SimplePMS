package dev.felipequeiroz.simplepms.repository;

import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadeHabitacionalRepository extends JpaRepository<UnidadeHabitacional, Long> {

    boolean existsByNomeUhIgnoreCase(String s);

    List<UnidadeHabitacional> findAllByAtivoTrue();
}

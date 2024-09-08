package dev.felipequeiroz.simplepms.repository;

import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadeHabitacionalRepository extends JpaRepository<UnidadeHabitacional, Long> {

    boolean existsByNomeUhIgnoreCase(String s);

}

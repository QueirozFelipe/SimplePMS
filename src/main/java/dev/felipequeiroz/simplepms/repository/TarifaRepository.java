package dev.felipequeiroz.simplepms.repository;

import dev.felipequeiroz.simplepms.domain.Tarifa;
import dev.felipequeiroz.simplepms.dto.tarifa.DetalhamentoTarifaDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    List<Tarifa> findAllByAtivoTrue();
}

package com.aether.backend.repository;

import com.aether.backend.entity.Risorsa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RisorsaRepository extends JpaRepository<Risorsa, Long> {

    boolean existsByCodiceIgnoreCase(String codice);

    Optional<Risorsa> findByCodiceIgnoreCase(String codice);

    List<Risorsa> findByAttivo(boolean attivo);
}

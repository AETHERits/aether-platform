package com.aether.backend.repository;

import com.aether.backend.entity.Colonia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColoniaRepository extends JpaRepository<Colonia, Long> {
    List<Colonia> findByCancellatoFalse();
    boolean existsByCodice(String codice);
    boolean existsByCodiceAndIdColoniaNot(String codice, Long id);
    Optional<Colonia> findByCodice(String codice);
}
package com.aether.backend.repository;

import com.aether.backend.entity.Colonia;
import com.aether.backend.entity.StatoOperativoColonia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository Spring Data JPA per la gestione della persistenza dell'entità Colonia.
 */
@Repository
public interface ColoniaRepository extends JpaRepository<Colonia, Long> {

    /**
     * Recupera tutte le colonie che non sono state cancellate logicamente.
     * Utilizzato per l'endpoint di elenco basi marziane (ARES-101).
     */
    List<Colonia> findByCancellatoFalse();

    /**
     * Cerca una colonia attiva tramite il suo codice univoco business (es. "COL-ARES-01").
     */
    Optional<Colonia> findByCodiceAndCancellatoFalse(String codice);

    /**
     * Filtra le colonie in base allo stato operativo (es. ATTIVA, SOSPESA, ecc.).
     */
    List<Colonia> findByStatoOperativoColoniaAndCancellatoFalse(StatoOperativoColonia statoOperativoColonia);

    /**
     * Verifica l'esistenza di una colonia con un determinato codice.
     */
    boolean existsByCodiceAndCancellatoFalse(String codice);
}
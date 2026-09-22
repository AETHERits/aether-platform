package com.aether.backend.colonie;

import org.springframework.data.jpa.repository      .JpaRepository;

/**
 * Accesso dati alla tabella "colonie".
 * Per questa feature si usa solo findAll() (select del form):
 * il repository esiste per completare il pattern, non per scritture.
 */
public interface ColoniaRepository extends JpaRepository<Colonia, Long> {
}
package com.aether.backend.repository;

import com.aether.backend.entity.Colonia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.Collection;

/**
 * Accesso dati alla tabella "colonie".
 * Per questa feature si usa solo findAll() (select del form):
 * il repository esiste per completare il pattern, non per scritture.
 */
public interface ColoniaRepository extends JpaRepository<Colonia, Long> {
    List<Colonia> findByCancellatoFalse();
}
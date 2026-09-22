package com.aether.backend.incidenti;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Accesso dati alla tabella "incidenti".
 *
 * JpaRepository fornisce già save, findAll, findById, count:
 * aggiungere query custom (@Query o metodi derivati) qui sotto
 * solo quando serviranno (es. ricerca per colonia o per stato).
 */
public interface IncidenteRepository extends JpaRepository<Incidente, Long> {
}
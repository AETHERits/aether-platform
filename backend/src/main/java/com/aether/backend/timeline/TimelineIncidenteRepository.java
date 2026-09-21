package com.aether.backend.timeline;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Accesso dati alla tabella "timeline_incidente" (storico eventi).
 * Oggi si usa solo save(); in futuro servirà una query per leggere
 * lo storico di un incidente (es. findByIdIncidenteOrderByDataEvento).
 */
public interface TimelineIncidenteRepository extends JpaRepository<TimelineIncidente, Long> {
}
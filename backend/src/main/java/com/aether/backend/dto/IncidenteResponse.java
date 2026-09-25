package com.aether.backend.dto;

import com.aether.backend.entity.Incidente;
import com.aether.backend.entity.Severita;
import com.aether.backend.entity.StatoIncidente;

import java.time.Instant;

public record IncidenteResponse(
        Long id,
        String codice,
        Integer idColonia,
        String titolo,
        String descrizione,
        Severita severita,
        StatoIncidente stato,
        Instant registratoIl
) {
    public static IncidenteResponse from(Incidente incidente) {
        return new IncidenteResponse(
                incidente.getId(),
                incidente.getCodice(),
                incidente.getColonia() != null ? incidente.getColonia().getId() : null,
                incidente.getTitolo(),
                incidente.getDescrizione(),
                incidente.getSeverita(),
                incidente.getStato(),
                incidente.getRegistratoIl()
        );
    }
}

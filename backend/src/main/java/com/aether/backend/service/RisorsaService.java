package com.aether.backend.service;

import com.aether.backend.dto.RisorsaCreateRequest;
import com.aether.backend.dto.RisorsaResponse;
import com.aether.backend.dto.RisorsaUpdateRequest;

import java.util.List;

public interface RisorsaService {

    RisorsaResponse crea(RisorsaCreateRequest request);

    RisorsaResponse aggiorna(Long id, RisorsaUpdateRequest request);

    RisorsaResponse trovaPerId(Long id);

    /**
     * Elenco risorse (Acceptance Criteria: "Elenco consultabile").
     *
     * @param soloAttive se non nullo, filtra per stato attivo/disattivo;
     *                   se nullo, restituisce tutte le risorse.
     */
    List<RisorsaResponse> elenca(Boolean soloAttive);

    RisorsaResponse attiva(Long id);

    RisorsaResponse disattiva(Long id);
}

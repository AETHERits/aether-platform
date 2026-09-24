package com.aether.backend.service;

import com.aether.backend.dto.AggiornaMissioneRequest;
import com.aether.backend.dto.CreaMissioneRequest;
import com.aether.backend.dto.MissioneResponse;
import com.aether.backend.entity.StatoMissione;

import java.util.List;

public interface MissioneService {

    MissioneResponse creaBozza(CreaMissioneRequest request);

    List<MissioneResponse> getAll();

    MissioneResponse getById(Long id);

    /** Modifica completa dei dati; consentita solo se la missione e' in DRAFT. */
    MissioneResponse aggiorna(Long id, AggiornaMissioneRequest request);

    /** Cambio di stato secondo le transizioni definite in {@link StatoMissione}. */
    MissioneResponse cambiaStato(Long id, StatoMissione nuovoStato);

    /** Eliminazione fisica; consentita solo se la missione e' in DRAFT (altrimenti va annullata). */
    void elimina(Long id);
}

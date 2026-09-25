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

    MissioneResponse aggiorna(Long id, AggiornaMissioneRequest request);

    MissioneResponse cambiaStato(Long id, StatoMissione nuovoStato);

    void elimina(Long id);
}

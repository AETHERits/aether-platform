package com.aether.backend.service;

import com.aether.backend.dto.NuovoIncidenteRequest;
import com.aether.backend.entity.Incidente;

import java.util.List;

public interface IncidenteService {
    Incidente registra(NuovoIncidenteRequest req);

    List<Incidente> ottieniTutti();
}

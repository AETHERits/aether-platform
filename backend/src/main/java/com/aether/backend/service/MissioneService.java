package com.aether.backend.service;

import com.aether.backend.dto.CreaMissioneRequest;
import com.aether.backend.dto.MissioneResponse;

import java.util.List;

public interface MissioneService {

    MissioneResponse creaBozza(CreaMissioneRequest request);

    List<MissioneResponse> getAll();

    MissioneResponse getById(Long id);
}

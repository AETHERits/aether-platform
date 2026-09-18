package com.example.missions.service;

import com.example.missions.dto.MissionCreateRequest;
import com.example.missions.dto.MissionResponse;

import java.util.List;

public interface MissionService {

    MissionResponse createMission(MissionCreateRequest request);

    MissionResponse getMissionByCode(String missionCode);

    List<MissionResponse> getAllMissions();
}

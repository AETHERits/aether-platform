package com.example.missions.repository;

import com.example.missions.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    boolean existsByMissionCode(String missionCode);

    Optional<Mission> findByMissionCode(String missionCode);
}

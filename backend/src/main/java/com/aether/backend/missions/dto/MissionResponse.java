package com.example.missions.dto;

import com.example.missions.entity.Mission;
import com.example.missions.entity.MissionStatus;
import com.example.missions.entity.Priority;
import com.example.missions.entity.RiskLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MissionResponse {

    private Long id;
    private String missionCode;
    private String objective;
    private Long baseId;
    private String baseName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Priority priority;
    private RiskLevel riskLevel;
    private Integer riskScore;
    private MissionStatus status;
    private LocalDateTime createdAt;

    public static MissionResponse fromEntity(Mission m) {
        return MissionResponse.builder()
                .id(m.getId())
                .missionCode(m.getMissionCode())
                .objective(m.getObjective())
                .baseId(m.getBase().getId())
                .baseName(m.getBase().getName())
                .startDate(m.getStartDate())
                .endDate(m.getEndDate())
                .priority(m.getPriority())
                .riskLevel(m.getRiskLevel())
                .riskScore(m.getRiskScore())
                .status(m.getStatus())
                .createdAt(m.getCreatedAt())
                .build();
    }
}

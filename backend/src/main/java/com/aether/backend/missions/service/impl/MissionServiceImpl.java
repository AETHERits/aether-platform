package com.example.missions.service.impl;

import com.example.missions.dto.MissionCreateRequest;
import com.example.missions.dto.MissionResponse;
import com.example.missions.entity.Base;
import com.example.missions.entity.Mission;
import com.example.missions.exception.BusinessRuleViolationException;
import com.example.missions.exception.DuplicateResourceException;
import com.example.missions.exception.ResourceNotFoundException;
import com.example.missions.repository.BaseRepository;
import com.example.missions.repository.MissionRepository;
import com.example.missions.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService {

    /** Finestra temporale massima consentita per una missione, regola di business esemplificativa. */
    private static final long MAX_DURATION_DAYS = 365;

    private final MissionRepository missionRepository;
    private final BaseRepository baseRepository;

    @Override
    @Transactional
    public MissionResponse createMission(MissionCreateRequest request) {

        // 1) Codice missione univoco
        if (missionRepository.existsByMissionCode(request.getMissionCode())) {
            throw new DuplicateResourceException(
                    "Esiste già una missione con codice '" + request.getMissionCode() + "'");
        }

        // 2) Base/colonia esistente
        Base base = baseRepository.findById(request.getBaseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nessuna base/colonia trovata con id " + request.getBaseId()));

        // 3) Finestra temporale coerente
        validateTimeWindow(request.getStartDate(), request.getEndDate());

        // 4) Priorità e rischio controllati
        //    Priority/RiskLevel sono già ristretti dall'enum in deserializzazione;
        //    qui verifichiamo la coerenza incrociata tra priorità e rischio.
        validatePriorityRiskConsistency(request);

        // 5) Stato iniziale DRAFT: impostato dall'entità stessa in @PrePersist,
        //    qui costruiamo semplicemente l'entità senza esporre un campo status nel DTO di input.
        Mission mission = Mission.builder()
                .missionCode(request.getMissionCode().trim())
                .objective(request.getObjective().trim())
                .base(base)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .priority(request.getPriority())
                .riskLevel(request.getRiskLevel())
                .riskScore(request.getRiskScore())
                .build();

        Mission saved = missionRepository.save(mission);
        return MissionResponse.fromEntity(saved);
    }

    @Override
    public MissionResponse getMissionByCode(String missionCode) {
        Mission mission = missionRepository.findByMissionCode(missionCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nessuna missione trovata con codice '" + missionCode + "'"));
        return MissionResponse.fromEntity(mission);
    }

    @Override
    public List<MissionResponse> getAllMissions() {
        return missionRepository.findAll().stream()
                .map(MissionResponse::fromEntity)
                .toList();
    }

    private void validateTimeWindow(LocalDateTime start, LocalDateTime end) {
        if (!end.isAfter(start)) {
            throw new BusinessRuleViolationException(
                    "La data di fine (" + end + ") deve essere successiva alla data di inizio (" + start + ")");
        }

        if (start.isBefore(LocalDateTime.now())) {
            throw new BusinessRuleViolationException(
                    "La data di inizio non può essere nel passato");
        }

        long durationDays = ChronoUnit.DAYS.between(start, end);
        if (durationDays > MAX_DURATION_DAYS) {
            throw new BusinessRuleViolationException(
                    "La durata della missione (" + durationDays + " giorni) supera il massimo consentito di "
                            + MAX_DURATION_DAYS + " giorni");
        }
    }

    private void validatePriorityRiskConsistency(MissionCreateRequest request) {
        // Esempio di regola di business: una missione CRITICAL non può avere un rischio
        // dichiarato basso, per evitare incoerenze tra priorità e rischio percepito.
        boolean criticalWithLowRisk = request.getPriority() == com.example.missions.entity.Priority.CRITICAL
                && request.getRiskLevel() == com.example.missions.entity.RiskLevel.LOW;

        if (criticalWithLowRisk) {
            throw new BusinessRuleViolationException(
                    "Una missione con priorità CRITICAL non può avere livello di rischio LOW: "
                            + "verificare la classificazione del rischio");
        }
    }
}

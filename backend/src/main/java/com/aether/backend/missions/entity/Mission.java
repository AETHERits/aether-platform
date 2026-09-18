package com.example.missions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "missions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Codice missione univoco (es. "MSN-2026-001"). Vincolo di unicità a livello DB + controllo applicativo. */
    @Column(name = "mission_code", nullable = false, unique = true, length = 50)
    private String missionCode;

    @Column(nullable = false, length = 500)
    private String objective;

    /** Riferimento alla base/colonia: deve esistere in DB, verificato dal service prima del salvataggio. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "base_id", nullable = false)
    private Base base;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false, length = 20)
    private RiskLevel riskLevel;

    /** Punteggio di rischio numerico, 0-100, per un controllo più fine oltre al livello qualitativo. */
    @Column(name = "risk_score", nullable = false)
    private Integer riskScore;

    /** Impostato sempre a DRAFT alla creazione: mai accettato in input dal client. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MissionStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = MissionStatus.DRAFT;
    }

}

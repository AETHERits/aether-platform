package com.aether.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * Colonne di audit comuni a molte tabelle (data_creazione, ultima_modifica).
 * ultima_modifica e' NOT NULL senza default nel DB: la valorizziamo qui,
 * cosi' il service non deve occuparsene.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class Auditable {

    @Column(name = "data_creazione", nullable = false, updatable = false)
    private OffsetDateTime dataCreazione;

    @Column(name = "ultima_modifica", nullable = false)
    private OffsetDateTime ultimaModifica;

    @PrePersist
    protected void onCreate() {
        OffsetDateTime adesso = OffsetDateTime.now();
        if (dataCreazione == null) {
            dataCreazione = adesso;
        }
        ultimaModifica = adesso;
    }

    @PreUpdate
    protected void onUpdate() {
        ultimaModifica = OffsetDateTime.now();
    }
}


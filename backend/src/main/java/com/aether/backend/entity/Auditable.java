package com.aether.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

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


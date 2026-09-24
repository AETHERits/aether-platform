package com.aether.backend.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "colonie", schema = "aether")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Colonia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colonia")
    private Long idColonia;

    @Column(name = "codice", nullable = false, unique = true, length = 10)
    private String codice;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "area", length = 100)
    private String area;

    // Precision e scale per i valori geografici DECIMAL(9,6)
    @Column(name = "latitudine", precision = 9, scale = 6)
    private BigDecimal latitudine;

    @Column(name = "longitudine", precision = 9, scale = 6)
    private BigDecimal longitudine;

    // Salvataggio dell'enum come stringa per aderenza al vincolo CHECK del DB
    @Enumerated(EnumType.STRING)
    @Column(name = "stato_operativo", nullable = false, length = 20)
    private StatoOperativoColonia statoOperativoColonia;

    @Column(name = "id_responsabile")
    private Long idResponsabile;

    @Column(name = "capacita_massima")
    private Integer capacitaMassima;

    @Column(name = "data_creazione", nullable = false, updatable = false)
    private OffsetDateTime dataCreazione;

    @Column(name = "ultima_modifica", nullable = false)
    private OffsetDateTime ultimaModifica;

    // Bandiera per la cancellazione logica (soft delete)
    @Builder.Default
    @Column(name = "cancellato", nullable = false)
    private Boolean cancellato = false;

    /*
     * Callback JPA per gestire automaticamente le date di creazione e aggiornamento
     */
    @PrePersist
    protected void onCreate() {
        if (dataCreazione == null) {
            dataCreazione = OffsetDateTime.now();
        }
        ultimaModifica = OffsetDateTime.now();
        if (cancellato == null) {
            cancellato = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        ultimaModifica = OffsetDateTime.now();
    }
}
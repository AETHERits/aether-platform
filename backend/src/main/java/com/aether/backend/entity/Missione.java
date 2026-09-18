package com.aether.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "missioni")
@Getter
@Setter
@NoArgsConstructor
public class Missione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_missione")
    private Long id;

    @Column(name = "codice", nullable = false, length = 20)
    private String codice;

    @Column(name = "obiettivo", nullable = false, length = 255)
    private String obiettivo;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "id_colonia", nullable = false)
    private Integer idColonia;

    @Column(name = "id_tipologia", nullable = false)
    private Integer idTipologia;

    @Column(name = "data_inizio_prevista", nullable = false)
    private OffsetDateTime dataInizioPrevista;

    @Column(name = "data_fine_prevista", nullable = false)
    private OffsetDateTime dataFinePrevista;

    @Enumerated(EnumType.STRING)
    @Column(name = "priorita", nullable = false, length = 20)
    private Priorita priorita;

    @Enumerated(EnumType.STRING)
    @Column(name = "rischio", nullable = false, length = 20)
    private Rischio rischio;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false, length = 20)
    private StatoMissione stato;

    @Column(name = "id_responsabile", nullable = false)
    private Long idResponsabile;

    @Column(name = "data_creazione", nullable = false, updatable = false)
    private OffsetDateTime dataCreazione;

    @Column(name = "ultima_modifica", nullable = false)
    private OffsetDateTime ultimaModifica;
}

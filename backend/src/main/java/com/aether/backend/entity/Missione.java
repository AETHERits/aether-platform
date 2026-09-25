package com.aether.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "missioni")
@Getter
@Setter
@NoArgsConstructor
public class Missione extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_missione")
    private Long id;

    @Column(name = "codice", nullable = false, unique = true, length = 20)
    private String codice;

    @Column(name = "obiettivo", nullable = false, length = 255)
    private String obiettivo;

    @Column(name = "descrizione")
    private String descrizione;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_colonia", nullable = false)
    private Colonia colonia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipologia", nullable = false)
    private TipologiaMissione tipologia;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_responsabile", nullable = false)
    private Astronauta responsabile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approvato_da")
    private Utente approvatoDa;

    @Column(name = "data_approvazione")
    private OffsetDateTime dataApprovazione;

    @Enumerated(EnumType.STRING)
    @Column(name = "livello_sicurezza", nullable = false, length = 20)
    private LivelloSicurezza livelloSicurezza = LivelloSicurezza.STANDARD;

    @Column(name = "motivo_annullamento")
    private String motivoAnnullamento;
}


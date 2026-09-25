package com.aether.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "incidenti")
@Getter
@Setter
@NoArgsConstructor
public class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidente")
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_colonia", nullable = false)
    private Colonia colonia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_missione")
    private Missione missione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_habitat")
    private Habitat habitat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asset")
    private Asset asset;

    @Column(nullable = false, length = 150)
    private String titolo;

    @Column(nullable = false)
    private String descrizione;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Severita severita;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatoIncidente stato;

    @Column(name = "causa_radice")
    private String causaRadice;

    @Column(name = "azioni_correttive")
    private String azioniCorrettive;

    @Column(name = "azioni_preventive")
    private String azioniPreventive;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "riportato_da", nullable = false)
    private Utente riportatoDa;

    @Column(name = "riportato_il", nullable = false, updatable = false)
    private Instant registratoIl;

    @Column(name = "chiuso_il")
    private Instant chiusoIl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chiuso_da")
    private Utente chiusoDa;

    @Column(name = "motivazione_chiusura", length = 255)
    private String motivazioneChiusura;

    @PrePersist
    protected void onCreate() {
        if (registratoIl == null) {
            registratoIl = Instant.now();
        }
    }
}

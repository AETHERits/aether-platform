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

    // N:1 -> colonie (obbligatoria)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_colonia", nullable = false)
    private Colonia colonia;

    // N:1 -> missioni (opzionale)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_missione")
    private Missione missione;

    // N:1 -> habitat (opzionale)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_habitat")
    private Habitat habitat;

    // N:1 -> asset_tecnici (opzionale)
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

    // N:1 -> utenti (chi ha segnalato)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "riportato_da", nullable = false)
    private Utente riportatoDa;

    @Column(name = "riportato_il", nullable = false, updatable = false)
    private Instant registratoIl;

    @Column(name = "chiuso_il")
    private Instant chiusoIl;

    // N:1 -> utenti (chi ha chiuso; null finche' l'incidente e' aperto)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chiuso_da")
    private Utente chiusoDa;

    @Column(name = "motivazione_chiusura", length = 255)
    private String motivazioneChiusura;

    /** riportato_il e' NOT NULL: Hibernate inserirebbe null e ignorerebbe il default del DB. */
    @PrePersist
    protected void onCreate() {
        if (registratoIl == null) {
            registratoIl = Instant.now();
        }
    }
}

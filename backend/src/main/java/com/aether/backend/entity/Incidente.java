package com.aether.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "incidenti")
public class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidente")
    private Long id;

    @Column(nullable = false, unique = true)
    private String codice;

    @Column(name = "id_colonia", nullable = false)
    private Long idColonia;

    @Column(name = "id_missione")
    private Long idMissione;

    @Column(name = "id_habitat")
    private Long idHabitat;

    @Column(name = "id_asset")
    private Long idAsset;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String descrizione;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severita severita;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoIncidente stato;

    @Column(name = "riportato_da", nullable = false)
    private Long riportatoDa;

    @Column(name = "riportato_il", nullable = false, updatable = false)
    private Instant registratoIl;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodice() { return codice; }
    public void setCodice(String codice) { this.codice = codice; }

    public Long getIdColonia() { return idColonia; }
    public void setIdColonia(Long idColonia) { this.idColonia = idColonia; }

    public Long getIdMissione() { return idMissione; }
    public void setIdMissione(Long idMissione) { this.idMissione = idMissione; }

    public Long getIdHabitat() { return idHabitat; }
    public void setIdHabitat(Long idHabitat) { this.idHabitat = idHabitat; }

    public Long getIdAsset() { return idAsset; }
    public void setIdAsset(Long idAsset) { this.idAsset = idAsset; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public Severita getSeverita() { return severita; }
    public void setSeverita(Severita severita) { this.severita = severita; }

    public StatoIncidente getStato() { return stato; }
    public void setStato(StatoIncidente stato) { this.stato = stato; }

    public Long getRiportatoDa() { return riportatoDa; }
    public void setRiportatoDa(Long riportatoDa) { this.riportatoDa = riportatoDa; }

    public Instant getRegistratoIl() { return registratoIl; }
    public void setRegistratoIl(Instant registratoIl) { this.registratoIl = registratoIl; }
}
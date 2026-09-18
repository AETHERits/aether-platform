package com.aether.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity JPA che rappresenta l'anagrafica di una tipologia di risorsa
 * gestita dalle basi (es. "Acqua potabile", "Ossigeno", "Kit EVA"...).
 *
 * NB: rappresenta il CATALOGO delle tipologie di risorsa, non le giacenze
 * fisiche nei magazzini (quelle vivono in altre tabelle, es. livelli_giacenza).
 */
@Entity
@Table(name = "risorse")
public class Risorsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_risorsa")
    private Long idRisorsa;

    /**
     * Codice identificativo univoco della risorsa (es. "WATER", "O2", "EVA_KIT").
     * Immutabile dopo la creazione (scelta di design: evita di rompere i
     * riferimenti storici nei movimenti di magazzino se il codice cambiasse).
     */
    @Column(name = "codice", nullable = false, unique = true, length = 40)
    private String codice;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "unita_misura", nullable = false, length = 20)
    private String unitaMisura;

    /**
     * Attivabile/disattivabile (Acceptance Criteria): una risorsa disattivata
     * non deve poter essere selezionata in nuovi movimenti/rifornimenti, ma
     * resta consultabile per non spezzare lo storico (cancellazione logica).
     */
    @Column(name = "attivo", nullable = false)
    private boolean attivo = true;

    protected Risorsa() {
        // richiesto da JPA
    }

    public Risorsa(String codice, String nome, String unitaMisura) {
        this.codice = codice;
        this.nome = nome;
        this.unitaMisura = unitaMisura;
        this.attivo = true;
    }

    public Long getIdRisorsa() {
        return idRisorsa;
    }

    public String getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnitaMisura() {
        return unitaMisura;
    }

    public void setUnitaMisura(String unitaMisura) {
        this.unitaMisura = unitaMisura;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void attiva() {
        this.attivo = true;
    }

    public void disattiva() {
        this.attivo = false;
    }
}

package com.aether.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "risorse")
public class Risorsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_risorsa")
    private Long idRisorsa;

    @Column(name = "codice", nullable = false, unique = true, length = 40)
    private String codice;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "unita_misura", nullable = false, length = 20)
    private String unitaMisura;

    @Column(name = "attivo", nullable = false)
    private boolean attivo = true;

    protected Risorsa() {
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

package com.aether.backend.timeline;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "timeline_incidente")
public class TimelineIncidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long id;

    @Column(name = "id_incidente", nullable = false)
    private Long idIncidente;

    @Column(name = "tipo_evento", nullable = false)
    private String tipoEvento;

    @Column(name = "descrizione", nullable = false, columnDefinition = "TEXT")
    private String descrizione;

    @Column(name = "registrato_da", nullable = false)
    private Long registratoDa;

    @Column(name = "data_evento", nullable = false)
    private Instant dataEvento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdIncidente() { return idIncidente; }
    public void setIdIncidente(Long idIncidente) { this.idIncidente = idIncidente; }

    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public Long getRegistratoDa() { return registratoDa; }
    public void setRegistratoDa(Long registratoDa) { this.registratoDa = registratoDa; }

    public Instant getDataEvento() { return dataEvento; }
    public void setDataEvento(Instant dataEvento) { this.dataEvento = dataEvento; }
}
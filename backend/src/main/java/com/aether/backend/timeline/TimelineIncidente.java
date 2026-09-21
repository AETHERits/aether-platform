package com.aether.backend.timeline;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

/**
 * Entity JPA mappata sulla tabella ESISTENTE "timeline_incidente":
 * lo STORICO degli eventi di un incidente (una riga per evento).
 *
 * Differenza chiave con Incidente:
 * - Incidente = fotografia dello stato ATTUALE;
 * - TimelineIncidente = album degli eventi passati
 *   (REGISTRAZIONE, PRESA_IN_CARICO, CHIUSURA, ...).
 *
 * [HEL-501]: alla creazione di un incidente il service inserisce qui
 * il primo evento, di tipo REGISTRAZIONE, con operatore e timestamp.
 *
 * NOTA: i nomi colonna (@Column) vanno verificati contro il CREATE TABLE
 * reale / seed del DB e allineati se diversi.
 */
@Entity
@Table(name = "timeline_incidente")
public class TimelineIncidente {

    /** Chiave primaria dell'evento. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ID dell'incidente a cui l'evento si riferisce. */
    @Column(name = "id_incidente", nullable = false)
    private Long idIncidente;

    /** Tipo di evento: REGISTRAZIONE, PRESA_IN_CARICO, CHIUSURA, ... */
    @Column(name = "tipo_evento", nullable = false)
    private String tipoEvento;

    /** Operatore che ha compiuto/registrato l'evento ("registrato da"). */
    @Column(name = "registrato_da", nullable = false)
    private Long registratoDa;

    /** Momento in cui l'evento è avvenuto. */
    @Column(name = "data_evento", nullable = false)
    private Instant dataEvento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdIncidente() { return idIncidente; }
    public void setIdIncidente(Long idIncidente) { this.idIncidente = idIncidente; }

    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }

    public Long getRegistratoDa() { return registratoDa; }
    public void setRegistratoDa(Long registratoDa) { this.registratoDa = registratoDa; }

    public Instant getDataEvento() { return dataEvento; }
    public void setDataEvento(Instant dataEvento) { this.dataEvento = dataEvento; }
}
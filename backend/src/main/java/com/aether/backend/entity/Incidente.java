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

/**
 * Entity JPA mappata sulla tabella ESISTENTE "incidenti"
 * (creata da migration/seed: questo progetto non deve ricrearla,
 *  vedi spring.jpa.hibernate.ddl-auto=validate in application.yml).
 *
 * Rappresenta lo stato CORRENTE di un incidente operativo;
 * lo storico degli eventi vive invece in TimelineIncidente.
 *
 * Note di design [HEL-501]:
 * - "codice" e "registrato_il" sono generati SOLO dal service: il client
 *   non può inviarli né modificarli (criteri 4 e 5);
 * - "stato" è forzato a APERTO in creazione dal service (criterio 4);
 * - le chiavi esterne (id_colonia, id_missione, ...) sono mappate come ID
 *   semplici: le relazioni @ManyToOne potranno essere introdotte più avanti.
 */
@Entity
@Table(name = "incidenti")
public class Incidente {

    /** Chiave primaria surrogata (id progressivo). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Codice leggibile univoco (es. INC-004), generato dal service. */
    @Column(nullable = false, unique = true)
    private String codice;

    /** Colonia/base in cui è avvenuto l'evento: OBBLIGATORIA (criterio 1). */
    @Column(name = "id_colonia", nullable = false)
    private Long idColonia;

    /** Missione coinvolta (opzionale). */
    @Column(name = "id_missione")
    private Long idMissione;

    /** Habitat coinvolto (opzionale). */
    @Column(name = "id_habitat")
    private Long idHabitat;

    /** Asset/impianto coinvolto (opzionale). */
    @Column(name = "id_asset")
    private Long idAsset;

    /** Titolo sintetico dell'evento (criterio 2). */
    @Column(nullable = false)
    private String titolo;

    /** Descrizione dettagliata dell'accaduto (criterio 2). */
    @Column(nullable = false)
    private String descrizione;

    /** Severità: salvata come stringa maiuscola ("ALTA", ...). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severita severita;

    /** Stato ciclo di vita: APERTO alla creazione (criterio 4). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoIncidente stato;

    /** ID della persona/operatore che ha segnalato l'incidente. */
    @Column(name = "riportato_da", nullable = false)
    private Long riportatoDa;

    /** Timestamp di registrazione: imposto dal server (criterio 5). */
    @Column(name = "registrato_il", nullable = false)
    private Instant registratoIl;

    // ---------------------------------------------------------------------
    // Getter e setter: servono a JPA (persistenza) e a Jackson,
    // che usa i getter per serializzare l'entity nella risposta JSON.
    // ---------------------------------------------------------------------
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
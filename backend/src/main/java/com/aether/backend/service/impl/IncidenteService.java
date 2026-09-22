package com.aether.backend.incidenti;

import com.aether.backend.dto.NuovoIncidenteRequest;
import com.aether.backend.timeline.TimelineIncidente;
import com.aether.backend.timeline.TimelineIncidenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Logica di business della registrazione incidente.
 *
 * È QUI che i criteri di accettazione [HEL-501] diventano codice:
 * - criterio 4 "Stato iniziale OPEN": lo stato APERTO è imposto qui;
 * - criterio 5 "Timestamp registrato": l'orologio è quello del server;
 * né il controller né il frontend possono scavalcare queste regole.
 */
@Service
public class IncidenteService {

    private final IncidenteRepository incidenti;
    private final TimelineIncidenteRepository timeline;

    /** Injection delle dipendenze (constructor injection, pattern del team). */
    public IncidenteService(IncidenteRepository incidenti,
                            TimelineIncidenteRepository timeline) {
        this.incidenti = incidenti;
        this.timeline = timeline;
    }

    /**
     * Registra un nuovo incidente in stato APERTO e scrive in timeline
     * l'evento REGISTRAZIONE (chi e quando).
     *
     * Unica transazione: o vanno a buon fine ENTRAMBI gli insert
     * (incidente + evento storico), o nessuno dei due.
     *
     * @param req dati decisi dall'operatore, già validati nel controller
     * @return l'incidente salvato, completo di codice, stato e timestamp
     *         (è ciò che il form mostra nel messaggio di conferma)
     */
    @Transactional
    public Incidente registra(NuovoIncidenteRequest req) {
        Instant adesso = Instant.now();            // criterio 5: orologio del server

        Incidente i = new Incidente();
        i.setCodice(generaCodice());               // codice leggibile univoco
        i.setIdColonia(req.idColonia());           // criterio 1: base obbligatoria
        i.setIdMissione(req.idMissione());
        i.setIdHabitat(req.idHabitat());
        i.setIdAsset(req.idAsset());
        i.setTitolo(req.titolo());                 // criterio 2
        i.setDescrizione(req.descrizione());       // criterio 2
        i.setSeverita(req.severita());             // criterio 3
        i.setStato(StatoIncidente.APERTO);         // criterio 4: deciso qui, mai dal client
        i.setRegistratoIl(adesso);                 // criterio 5: deciso qui, mai dal client
        i.setRiportatoDa(req.riportatoDa());
        Incidente salvato = incidenti.save(i);

        // Primo evento dello storico: chi e quando ha registrato l'incidente.
        TimelineIncidente t = new TimelineIncidente();
        t.setIdIncidente(salvato.getId());
        t.setTipoEvento("REGISTRAZIONE");
        t.setRegistratoDa(req.riportatoDa());
        t.setDataEvento(adesso);
        timeline.save(t);

        return salvato;
    }

    /**
     * Genera un codice progressivo leggibile: INC-001, INC-002, ...
     *
     * TODO: passare a una sequence del DB quando ci saranno registrazioni
     *       concorrenti (count()+1 non è sicuro sotto concorrenza).
     */
    private String generaCodice() {
        return "INC-" + String.format("%03d", incidenti.count() + 1);
    }
}
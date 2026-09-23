package com.aether.backend.service.impl;

import com.aether.backend.dto.NuovoIncidenteRequest;
import com.aether.backend.entity.Incidente;
import com.aether.backend.entity.StatoIncidente;
import com.aether.backend.repository.IncidenteRepository;
import com.aether.backend.service.IncidenteService;
import com.aether.backend.timeline.TimelineIncidente;
import com.aether.backend.timeline.TimelineIncidenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class IncidenteServiceImpl implements IncidenteService {

    private final IncidenteRepository incidenti;
    private final TimelineIncidenteRepository timeline;

    public IncidenteServiceImpl(IncidenteRepository incidenti,
                            TimelineIncidenteRepository timeline) {
        this.incidenti = incidenti;
        this.timeline = timeline;
    }

    @Override
    @Transactional
    public Incidente registra(NuovoIncidenteRequest req) {
        Instant adesso = Instant.now();

        Incidente i = new Incidente();
        i.setCodice(generaCodice());
        i.setIdColonia(req.idColonia());
        i.setIdMissione(req.idMissione());
        i.setIdHabitat(req.idHabitat());
        i.setIdAsset(req.idAsset());
        i.setTitolo(req.titolo());
        i.setDescrizione(req.descrizione());
        i.setSeverita(req.severita());
        i.setStato(StatoIncidente.APERTO);
        i.setRegistratoIl(adesso);
        i.setRiportatoDa(req.riportatoDa());
        Incidente salvato = incidenti.save(i);

        TimelineIncidente t = new TimelineIncidente();
        t.setIdIncidente(salvato.getId());
        t.setTipoEvento("SEGNALAZIONE");
        t.setDescrizione("Registrazione dell'incidente " + salvato.getCodice());
        t.setRegistratoDa(req.riportatoDa());
        t.setDataEvento(adesso);
        timeline.save(t);

        return salvato;
    }

    private String generaCodice() {
        return "INC-" + String.format("%03d", incidenti.count() + 1);
    }

    @Override
    public List<Incidente> ottieniTutti() {
        return incidenti.findAll();
    }
}
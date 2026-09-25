package com.aether.backend.service.impl;

import com.aether.backend.dto.NuovoIncidenteRequest;
import com.aether.backend.entity.Asset;
import com.aether.backend.entity.Colonia;
import com.aether.backend.entity.Habitat;
import com.aether.backend.entity.Incidente;
import com.aether.backend.entity.Missione;
import com.aether.backend.entity.StatoIncidente;
import com.aether.backend.entity.Utente;
import com.aether.backend.exception.ResourceNotFoundException;
import com.aether.backend.repository.AssetRepository;
import com.aether.backend.repository.ColoniaRepository;
import com.aether.backend.repository.HabitatRepository;
import com.aether.backend.repository.IncidenteRepository;
import com.aether.backend.repository.MissioneRepository;
import com.aether.backend.repository.UtenteRepository;
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
    private final ColoniaRepository colonie;
    private final MissioneRepository missioni;
    private final HabitatRepository habitat;
    private final AssetRepository asset;
    private final UtenteRepository utenti;

    public IncidenteServiceImpl(IncidenteRepository incidenti,
                                TimelineIncidenteRepository timeline,
                                ColoniaRepository colonie,
                                MissioneRepository missioni,
                                HabitatRepository habitat,
                                AssetRepository asset,
                                UtenteRepository utenti) {
        this.incidenti = incidenti;
        this.timeline = timeline;
        this.colonie = colonie;
        this.missioni = missioni;
        this.habitat = habitat;
        this.asset = asset;
        this.utenti = utenti;
    }

    @Override
    @Transactional
    public Incidente registra(NuovoIncidenteRequest req) {
        Instant adesso = Instant.now();

        Colonia colonia = colonie.findById(toInt(req.idColonia(), "idColonia"))
                .orElseThrow(() -> new ResourceNotFoundException(req.idColonia()));
        Utente segnalatore = utenti.findById(req.riportatoDa())
                .orElseThrow(() -> new ResourceNotFoundException(req.riportatoDa()));

        Incidente i = new Incidente();
        i.setCodice(generaCodice());
        i.setColonia(colonia);
        i.setMissione(caricaMissione(req.idMissione()));
        i.setHabitat(caricaHabitat(req.idHabitat()));
        i.setAsset(caricaAsset(req.idAsset()));
        i.setTitolo(req.titolo());
        i.setDescrizione(req.descrizione());
        i.setSeverita(req.severita());
        i.setStato(StatoIncidente.APERTO);
        i.setRegistratoIl(adesso);
        i.setRiportatoDa(segnalatore);
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

    private Missione caricaMissione(Long id) {
        if (id == null) {
            return null;
        }
        return missioni.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private Habitat caricaHabitat(Long id) {
        if (id == null) {
            return null;
        }
        Integer habitatId = toInt(id, "idHabitat");
        return habitat.findById(habitatId).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private Asset caricaAsset(Long id) {
        if (id == null) {
            return null;
        }
        return asset.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private static Integer toInt(Long id, String campo) {
        if (id > Integer.MAX_VALUE || id < Integer.MIN_VALUE) {
            throw new IllegalArgumentException(campo + " non valido: " + id);
        }
        return id.intValue();
    }

    private String generaCodice() {
        return "INC-" + String.format("%03d", incidenti.count() + 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Incidente> ottieniTutti() {
        return incidenti.findAll();
    }
}

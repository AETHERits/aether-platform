package com.aether.backend.service.impl;

import com.aether.backend.dto.AggiornaMissioneRequest;
import com.aether.backend.dto.CreaMissioneRequest;
import com.aether.backend.dto.MissioneResponse;
import com.aether.backend.entity.Astronauta;
import com.aether.backend.entity.Colonia;
import com.aether.backend.entity.LivelloSicurezza;
import com.aether.backend.entity.Missione;
import com.aether.backend.entity.Priorita;
import com.aether.backend.entity.Rischio;
import com.aether.backend.entity.StatoMissione;
import com.aether.backend.entity.TipologiaMissione;
import com.aether.backend.exception.ConflictException;
import com.aether.backend.exception.ResourceNotFoundException;
import com.aether.backend.mapper.MissionApiMapper;
import com.aether.backend.repository.AstronautaRepository;
import com.aether.backend.repository.ColoniaRepository;
import com.aether.backend.repository.MissioneRepository;
import com.aether.backend.repository.TipologiaMissioneRepository;
import com.aether.backend.service.MissioneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class MissioneServiceImpl implements MissioneService {

    private final MissioneRepository missioneRepository;
    private final ColoniaRepository coloniaRepository;
    private final TipologiaMissioneRepository tipologiaMissioneRepository;
    private final AstronautaRepository astronautaRepository;

    public MissioneServiceImpl(MissioneRepository missioneRepository,
                               ColoniaRepository coloniaRepository,
                               TipologiaMissioneRepository tipologiaMissioneRepository,
                               AstronautaRepository astronautaRepository) {
        this.missioneRepository = missioneRepository;
        this.coloniaRepository = coloniaRepository;
        this.tipologiaMissioneRepository = tipologiaMissioneRepository;
        this.astronautaRepository = astronautaRepository;
    }

    // ------------------------------------------------------------------ CREATE

    @Override
    @Transactional
    public MissioneResponse creaBozza(CreaMissioneRequest request) {
        String codice = request.getCodice() == null || request.getCodice().isBlank()
                ? generaCodice()
                : request.getCodice().trim();
        if (missioneRepository.existsByCodice(codice)) {
            throw codiceDuplicato(codice);
        }
        request.setCodice(codice);
        validaPeriodo(request);

        Missione missione = new Missione();
        applicaDati(missione, request);
        missione.setStato(StatoMissione.DRAFT);

        // Con IDENTITY l'INSERT parte subito: un eventuale codice duplicato (race condition)
        // solleva DataIntegrityViolationException qui, gestita come 409 dall'handler.
        Missione salvata = missioneRepository.save(missione);
        return toResponse(salvata);
    }

    // -------------------------------------------------------------------- READ

    @Override
    @Transactional(readOnly = true)
    public List<MissioneResponse> getAll() {
        // La tipologia e' gia' caricata insieme alla missione tramite la relazione
        // @ManyToOne (LAZY, ma qui siamo dentro la transazione): niente piu' bisogno
        // di una query separata per evitare N+1 "a mano", la fa Hibernate.
        // Se il numero di missioni cresce, valuta @EntityGraph(attributePaths = "tipologia")
        // sul repository per forzare un unico JOIN invece di N query lazy.
        List<Missione> missioni = missioneRepository.findAll(Sort.by("id"));
        return missioni.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MissioneResponse getById(Long id) {
        return toResponse(trovaMissione(id));
    }

    // ------------------------------------------------------------------ UPDATE

    @Override
    @Transactional
    public MissioneResponse aggiorna(Long id, AggiornaMissioneRequest request) {
        Missione missione = trovaMissione(id);

        if (missione.getStato() != StatoMissione.DRAFT) {
            throw new ConflictException("Solo le missioni in stato DRAFT sono modificabili "
                    + "(stato attuale: " + missione.getStato() + "). "
                    + "Per cambiare stato usa PATCH /api/missioni/" + id + "/stato");
        }
        if (request.getCodice() == null || request.getCodice().isBlank()) {
            request.setCodice(missione.getCodice());
        } else {
            request.setCodice(request.getCodice().trim());
        }
        if (missioneRepository.existsByCodiceAndIdNot(request.getCodice(), id)) {
            throw codiceDuplicato(request.getCodice());
        }
        validaPeriodo(request);

        applicaDati(missione, request);

        // saveAndFlush: l'UPDATE parte ora, cosi' un'eventuale violazione di vincolo
        // viene tradotta correttamente in 409 invece di esplodere al commit.
        // ultima_modifica e' aggiornata da @PreUpdate (in Auditable).
        return toResponse(missioneRepository.saveAndFlush(missione));
    }

    @Override
    @Transactional
    public MissioneResponse cambiaStato(Long id, StatoMissione nuovoStato) {
        Missione missione = trovaMissione(id);
        StatoMissione attuale = missione.getStato();

        if (attuale == nuovoStato) {
            throw new ConflictException("La missione e' gia' nello stato " + attuale);
        }
        if (!attuale.puoTransitareA(nuovoStato)) {
            Set<StatoMissione> consentiti = attuale.transizioniConsentite();
            String elenco = consentiti.isEmpty() ? "nessuna (stato finale)" : consentiti.toString();
            throw new ConflictException("Transizione di stato non consentita: " + attuale + " -> " + nuovoStato
                    + ". Transizioni consentite da " + attuale + ": " + elenco);
        }

        missione.setStato(nuovoStato);
        return toResponse(missioneRepository.saveAndFlush(missione));
    }

    // ------------------------------------------------------------------ DELETE

    @Override
    @Transactional
    public void elimina(Long id) {
        Missione missione = trovaMissione(id);
        if (missione.getStato() != StatoMissione.DRAFT) {
            throw new ConflictException("Solo le missioni in stato DRAFT possono essere eliminate "
                    + "(stato attuale: " + missione.getStato() + "). "
                    + "Per le altre missioni usa il cambio di stato a CANCELLED");
        }
        missioneRepository.delete(missione);
    }

    // ----------------------------------------------------------------- helpers

    private Missione trovaMissione(Long id) {
        return missioneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private ConflictException codiceDuplicato(String codice) {
        return new ConflictException("Esiste gia' una missione con codice '" + codice + "'");
    }

    /** Validazione condivisa da creazione e modifica (AggiornaMissioneRequest estende CreaMissioneRequest). */
    private void validaPeriodo(CreaMissioneRequest r) {
        if (!r.getDataFinePrevista().isAfter(r.getDataInizioPrevista())) {
            throw new IllegalArgumentException(
                    "La data di fine prevista deve essere successiva alla data di inizio prevista");
        }
    }

    /**
     * Copia i dati modificabili dalla request all'entita' (stato e timestamp esclusi).
     * Qui, e non piu' in un metodo di validazione separato, si carica ogni relazione:
     * se l'id non esiste, findById lancia ResourceNotFoundException e la transazione
     * va in rollback, quindi il comportamento verso il chiamante e' identico a prima
     * (404 se colonia/tipologia/responsabile non esistono).
     */
    private void applicaDati(Missione missione, CreaMissioneRequest r) {
        Colonia colonia = coloniaRepository.findById(r.getIdColonia())
                .orElseThrow(() -> new ResourceNotFoundException(r.getIdColonia()));
        TipologiaMissione tipologia = risolviTipologia(r);
        Astronauta responsabile = risolviResponsabile(r, colonia);
        Priorita priorita = MissionApiMapper.parsePriorita(r.getPriorita());
        LivelloSicurezza livello = MissionApiMapper.parseLivello(r.getSafetyLevel());
        Rischio rischio = MissionApiMapper.parseRischio(r.getRischio(), livello);

        missione.setCodice(r.getCodice());
        missione.setObiettivo(r.getObiettivo());
        missione.setDescrizione(r.getDescrizione());
        missione.setColonia(colonia);
        missione.setTipologia(tipologia);
        missione.setDataInizioPrevista(r.getDataInizioPrevista());
        missione.setDataFinePrevista(r.getDataFinePrevista());
        missione.setPriorita(priorita);
        missione.setRischio(rischio);
        missione.setLivelloSicurezza(livello);
        missione.setResponsabile(responsabile);
    }

    private TipologiaMissione risolviTipologia(CreaMissioneRequest r) {
        if (r.getIdTipologia() != null) {
            return tipologiaMissioneRepository.findById(r.getIdTipologia())
                    .orElseThrow(() -> new ResourceNotFoundException(r.getIdTipologia()));
        }
        String nomeDb = MissionApiMapper.tipologiaDb(r.getMissionType());
        return tipologiaMissioneRepository.findByTipoIgnoreCase(nomeDb)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tipologia missione sconosciuta: " + r.getMissionType()));
    }

    private Astronauta risolviResponsabile(CreaMissioneRequest r, Colonia colonia) {
        if (r.getIdResponsabile() != null) {
            return astronautaRepository.findById(r.getIdResponsabile())
                    .orElseThrow(() -> new ResourceNotFoundException(r.getIdResponsabile()));
        }
        if (colonia.getResponsabile() != null) {
            return colonia.getResponsabile();
        }
        throw new IllegalArgumentException(
                "La colonia non ha un responsabile: indica idResponsabile nella richiesta");
    }

    private String generaCodice() {
        long n = missioneRepository.count() + 1;
        String codice;
        do {
            codice = String.format("MIS-%03d", n++);
        } while (missioneRepository.existsByCodice(codice));
        return codice;
    }

    private MissioneResponse toResponse(Missione missione) {
        // missione.getTipologia() e' gia' l'oggetto TipologiaMissione: nessuna query aggiuntiva
        // da scrivere qui (a differenza di prima, che ripescava la tipologia dal repository).
        return MissioneResponse.from(missione, missione.getTipologia());
    }
}

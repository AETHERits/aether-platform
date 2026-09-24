package com.aether.backend.service.impl;

import com.aether.backend.dto.AggiornaMissioneRequest;
import com.aether.backend.dto.CreaMissioneRequest;
import com.aether.backend.dto.MissioneResponse;
import com.aether.backend.entity.Missione;
import com.aether.backend.entity.StatoMissione;
import com.aether.backend.entity.TipologiaMissione;
import com.aether.backend.exception.ConflictException;
import com.aether.backend.exception.ResourceNotFoundException;
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
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        if (missioneRepository.existsByCodice(request.getCodice())) {
            throw codiceDuplicato(request.getCodice());
        }
        validaRiferimentiEPeriodo(request);

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
        List<Missione> missioni = missioneRepository.findAll(Sort.by("id"));

        // Una sola query per tutte le tipologie invece di una per missione (evita N+1)
        Set<Long> idTipologie = missioni.stream()
                .map(Missione::getIdTipologia)
                .collect(Collectors.toSet());
        Map<Long, TipologiaMissione> tipologie = idTipologie.isEmpty()
                ? Map.of()
                : tipologiaMissioneRepository.findAllById(idTipologie).stream()
                .collect(Collectors.toMap(TipologiaMissione::getId, Function.identity()));

        return missioni.stream()
                .map(m -> toResponse(m, tipologie.get(m.getIdTipologia())))
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
        if (missioneRepository.existsByCodiceAndIdNot(request.getCodice(), id)) {
            throw codiceDuplicato(request.getCodice());
        }
        validaRiferimentiEPeriodo(request);

        applicaDati(missione, request);

        // saveAndFlush: l'UPDATE parte ora, cosi' un'eventuale violazione di vincolo
        // viene tradotta correttamente in 409 invece di esplodere al commit.
        // ultima_modifica e' aggiornata da @PreUpdate.
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

    /** Validazioni condivise da creazione e modifica (AggiornaMissioneRequest estende CreaMissioneRequest). */
    private void validaRiferimentiEPeriodo(CreaMissioneRequest r) {
        if (!coloniaRepository.existsById(r.getIdColonia())) {
            throw new ResourceNotFoundException(r.getIdColonia());
        }
        if (!tipologiaMissioneRepository.existsById(r.getIdTipologia())) {
            throw new ResourceNotFoundException(r.getIdTipologia());
        }
        if (!astronautaRepository.existsById(r.getIdResponsabile())) {
            throw new ResourceNotFoundException(r.getIdResponsabile());
        }
        if (!r.getDataFinePrevista().isAfter(r.getDataInizioPrevista())) {
            throw new IllegalArgumentException(
                    "La data di fine prevista deve essere successiva alla data di inizio prevista");
        }
    }

    /** Copia i dati modificabili dalla request all'entita' (stato e timestamp esclusi). */
    private void applicaDati(Missione missione, CreaMissioneRequest r) {
        missione.setCodice(r.getCodice());
        missione.setObiettivo(r.getObiettivo());
        missione.setDescrizione(r.getDescrizione());
        missione.setIdColonia(r.getIdColonia());
        missione.setIdTipologia(r.getIdTipologia());
        missione.setDataInizioPrevista(r.getDataInizioPrevista());
        missione.setDataFinePrevista(r.getDataFinePrevista());
        missione.setPriorita(r.getPriorita());
        missione.setRischio(r.getRischio());
        missione.setIdResponsabile(r.getIdResponsabile());
    }

    private MissioneResponse toResponse(Missione missione) {
        TipologiaMissione tipologia = tipologiaMissioneRepository.findById(missione.getIdTipologia())
                .orElse(null);
        return toResponse(missione, tipologia);
    }

    private MissioneResponse toResponse(Missione missione, TipologiaMissione tipologia) {
        if (tipologia == null) {
            log.warn("Missione {} riferisce una tipologia inesistente (id {}): dato incoerente",
                    missione.getId(), missione.getIdTipologia());
        }
        return MissioneResponse.from(missione, tipologia);
    }
}

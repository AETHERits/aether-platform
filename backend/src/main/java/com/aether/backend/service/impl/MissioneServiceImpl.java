package com.aether.backend.service.impl;

import com.aether.backend.dto.CreaMissioneRequest;
import com.aether.backend.dto.MissioneResponse;
import com.aether.backend.entity.Missione;
import com.aether.backend.entity.StatoMissione;
import com.aether.backend.exception.ConflictException;
import com.aether.backend.exception.ResourceNotFoundException;
import com.aether.backend.repository.AstronautaRepository;
import com.aether.backend.repository.ColoniaRepository;
import com.aether.backend.repository.MissioneRepository;
import com.aether.backend.repository.TipologiaMissioneRepository;
import com.aether.backend.service.MissioneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

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

    @Override
    @Transactional
    public MissioneResponse creaBozza(CreaMissioneRequest request) {

        if (missioneRepository.existsByCodice(request.getCodice())) {
            throw new ConflictException("Esiste gia' una missione con codice '" + request.getCodice() + "'");
        }

        if (!coloniaRepository.existsById(Long.valueOf(request.getIdColonia()))) {
            throw new ResourceNotFoundException("Nessuna base/colonia trovata con id " + request.getIdColonia());
        }

        if (!tipologiaMissioneRepository.existsById(request.getIdTipologia())) {
            throw new ResourceNotFoundException("Nessuna tipologia di missione trovata con id " + request.getIdTipologia());
        }

        if (!astronautaRepository.existsById(request.getIdResponsabile())) {
            throw new ResourceNotFoundException("Nessun astronauta trovato con id " + request.getIdResponsabile());
        }

        if (!request.getDataFinePrevista().isAfter(request.getDataInizioPrevista())) {
            throw new IllegalArgumentException(
                    "La data di fine prevista deve essere successiva alla data di inizio prevista");
        }

        Missione missione = new Missione();
        missione.setCodice(request.getCodice());
        missione.setObiettivo(request.getObiettivo());
        missione.setDescrizione(request.getDescrizione());
        missione.setIdColonia(request.getIdColonia());
        missione.setIdTipologia(request.getIdTipologia());
        missione.setDataInizioPrevista(request.getDataInizioPrevista());
        missione.setDataFinePrevista(request.getDataFinePrevista());
        missione.setPriorita(request.getPriorita());
        missione.setRischio(request.getRischio());
        missione.setIdResponsabile(request.getIdResponsabile());
        missione.setStato(StatoMissione.DRAFT);

        OffsetDateTime adesso = OffsetDateTime.now();
        missione.setDataCreazione(adesso);
        missione.setUltimaModifica(adesso);

        Missione salvata = missioneRepository.save(missione);
        return MissioneResponse.from(salvata);
    }

    @Override
    public List<MissioneResponse> getAll() {
        return missioneRepository.findAll().stream()
                .map(MissioneResponse::from)
                .toList();
    }

    @Override
    public MissioneResponse getById(Long id) {
        Missione missione = missioneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nessuna missione trovata con id " + id));
        return MissioneResponse.from(missione);
    }
}

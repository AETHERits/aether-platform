package com.aether.backend.service;

import com.aether.backend.dto.RisorsaCreateRequest;
import com.aether.backend.dto.RisorsaResponse;
import com.aether.backend.dto.RisorsaUpdateRequest;
import com.aether.backend.entity.Risorsa;
import com.aether.backend.exception.CodiceRisorsaDuplicatoException;
import com.aether.backend.exception.RisorsaNonTrovataException;
import com.aether.backend.repository.RisorsaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RisorsaServiceImpl implements RisorsaService {

    private final RisorsaRepository risorsaRepository;

    public RisorsaServiceImpl(RisorsaRepository risorsaRepository) {
        this.risorsaRepository = risorsaRepository;
    }

    @Override
    public RisorsaResponse crea(RisorsaCreateRequest request) {
        // Acceptance Criteria: "Codice risorsa univoco" — controllo applicativo
        // esplicito (oltre al vincolo UNIQUE a livello di database, che resta
        // comunque la garanzia definitiva in caso di scritture concorrenti).
        if (risorsaRepository.existsByCodiceIgnoreCase(request.getCodice())) {
            throw new CodiceRisorsaDuplicatoException(request.getCodice());
        }

        Risorsa risorsa = new Risorsa(
                request.getCodice().trim(),
                request.getNome().trim(),
                request.getUnitaMisura().trim()
        );

        Risorsa salvata = risorsaRepository.save(risorsa);
        return RisorsaResponse.fromEntity(salvata);
    }

    @Override
    public RisorsaResponse aggiorna(Long id, RisorsaUpdateRequest request) {
        Risorsa risorsa = trovaEntitaOLancia(id);
        risorsa.setNome(request.getNome().trim());
        risorsa.setUnitaMisura(request.getUnitaMisura().trim());
        // nessuna chiamata esplicita a save(): l'entity è managed nella
        // transazione, JPA sincronizza le modifiche al commit (dirty checking).
        return RisorsaResponse.fromEntity(risorsa);
    }

    @Override
    @Transactional(readOnly = true)
    public RisorsaResponse trovaPerId(Long id) {
        return RisorsaResponse.fromEntity(trovaEntitaOLancia(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RisorsaResponse> elenca(Boolean soloAttive) {
        List<Risorsa> risorse = (soloAttive == null)
                ? risorsaRepository.findAll()
                : risorsaRepository.findByAttivo(soloAttive);

        return risorse.stream()
                .map(RisorsaResponse::fromEntity)
                .toList();
    }

    @Override
    public RisorsaResponse attiva(Long id) {
        Risorsa risorsa = trovaEntitaOLancia(id);
        risorsa.attiva();
        return RisorsaResponse.fromEntity(risorsa);
    }

    @Override
    public RisorsaResponse disattiva(Long id) {
        Risorsa risorsa = trovaEntitaOLancia(id);
        risorsa.disattiva();
        return RisorsaResponse.fromEntity(risorsa);
    }

    private Risorsa trovaEntitaOLancia(Long id) {
        return risorsaRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException(id));
    }
}

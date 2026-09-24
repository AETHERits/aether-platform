package com.aether.backend.service;

import com.aether.backend.dto.ColoniaResponse;
import com.aether.backend.entity.Colonia;
import com.aether.backend.mapper.ColoniaMapper;
import com.aether.backend.repository.ColoniaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servizio di logica di business per la gestione delle basi marziane.
 */
@Service
@RequiredArgsConstructor
public class ColoniaService {

    private final ColoniaRepository coloniaRepository;

    /**
     * Recupera tutte le basi marziane attive/sospese/non cancellate e le mappa in DTO.
     *
     * @return Lista di ColonyResponseDto. Se non vi sono basi, restituisce una lista vuota [].
     */
    @Transactional(readOnly = true)
    public List<ColoniaResponse> findAll() {

        return coloniaRepository.findByCancellatoFalse()
                .stream()
                .map(ColoniaMapper::toDTO)
                .toList();
    }

    /**
     * Mappatura di comodo dall'entità di dominio Colonia al DTO di risposta.
     */
    private ColoniaResponse mapToDto(Colonia colonia) {
        return ColoniaResponse.builder()
                .codice(colonia.getCodice())
                .nome(colonia.getNome())
                .stato(colonia.getStatoOperativoColonia())
                .build();
    }
}
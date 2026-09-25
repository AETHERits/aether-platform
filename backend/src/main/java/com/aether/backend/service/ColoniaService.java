package com.aether.backend.service;

import com.aether.backend.dto.ColoniaResponse;
import com.aether.backend.entity.Colonia;
import com.aether.backend.entity.StatoOperativoColonia;
import com.aether.backend.exception.ConflictException;
import com.aether.backend.exception.ResourceNotFoundException;
import com.aether.backend.mapper.ColoniaMapper;
import com.aether.backend.repository.ColoniaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ColoniaService {

    private final ColoniaRepository coloniaRepository;
    private final ColoniaMapper coloniaMapper;

    public ColoniaService(ColoniaRepository coloniaRepository, ColoniaMapper coloniaMapper) {
        this.coloniaRepository = coloniaRepository;
        this.coloniaMapper = coloniaMapper;
    }

    @Transactional(readOnly = true)
    public List<ColoniaResponse> findAll() {
        return coloniaRepository.findByCancellatoFalse().stream()
                .map(coloniaMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ColoniaResponse findById(Long id) {
        Colonia entity = coloniaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colony not found with ID: " + id));
        return coloniaMapper.toDTO(entity);
    }

    @Transactional
    public ColoniaResponse create(ColoniaResponse responseDto) {
        if (coloniaRepository.existsByCodice(responseDto.getCodice())) {
            throw new ConflictException("A colony with code " + responseDto.getCodice() + " already exists.");
        }

        Colonia entity = coloniaMapper.toEntity(responseDto);
        entity.setCancellato(false);
        Colonia saved = coloniaRepository.save(entity);
        return coloniaMapper.toDTO(saved);
    }

    @Transactional
    public ColoniaResponse update(Long id, ColoniaResponse responseDto) {
        Colonia entity = coloniaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update. Colony not found with ID: " + id));

        if (coloniaRepository.existsByCodiceAndIdColoniaNot(responseDto.getCodice(), id)) {
            throw new ConflictException("The code " + responseDto.getCodice() + " is already used by another colony.");
        }

        entity.setCodice(responseDto.getCodice());
        entity.setNome(responseDto.getNome());

        // Updates coordinates on existing entity
        coloniaMapper.parseAndSetCoordinates(responseDto.getCoordinate(), entity);

        entity.setStatoOperativoColonia(responseDto.getStatoOperativoColonia());
        if (responseDto.getCancellato() != null) {
            entity.setCancellato(responseDto.getCancellato());
        }

        Colonia updated = coloniaRepository.save(entity);
        return coloniaMapper.toDTO(updated);
    }

    @Transactional
    public ColoniaResponse patch(Long id, ColoniaResponse responseDto) {
        Colonia entity = coloniaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot patch. Colony not found with ID: " + id));

        if (responseDto.getCodice() != null && !responseDto.getCodice().isBlank()) {
            if (coloniaRepository.existsByCodiceAndIdColoniaNot(responseDto.getCodice(), id)) {
                throw new ConflictException("The code " + responseDto.getCodice() + " is already used by another colony.");
            }
            entity.setCodice(responseDto.getCodice());
        }

        if (responseDto.getNome() != null && !responseDto.getNome().isBlank()) {
            entity.setNome(responseDto.getNome());
        }

        if (responseDto.getCoordinate() != null) {
            coloniaMapper.parseAndSetCoordinates(responseDto.getCoordinate(), entity);
        }

        if (responseDto.getStatoOperativoColonia() != null) {
            entity.setStatoOperativoColonia(responseDto.getStatoOperativoColonia());
        }

        if (responseDto.getCancellato() != null) {
            entity.setCancellato(responseDto.getCancellato());
        }

        Colonia patched = coloniaRepository.save(entity);
        return coloniaMapper.toDTO(patched);
    }

    @Transactional
    public void deleteOrDeactivate(Long id) {
        Colonia entity = coloniaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. Colony not found with ID: " + id));

        // Soft delete logic according to BR-010 / COL-006
        entity.setCancellato(true);
        entity.setStatoOperativoColonia(StatoOperativoColonia.DISMESSA);
        coloniaRepository.save(entity);
    }
}
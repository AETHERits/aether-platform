package com.aether.backend.service.impl;

import com.aether.backend.dto.AssetDTO;
import com.aether.backend.entity.TipoAsset;
import com.aether.backend.exception.ConflictException;
import com.aether.backend.exception.ResourceNotFoundException;
import com.aether.backend.mapper.AssetMapper;
import com.aether.backend.repository.TipoAssetRepository;
import com.aether.backend.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final TipoAssetRepository tipoAssetRepository;
    private final AssetMapper assetMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AssetDTO> getAllAssets() {
        return tipoAssetRepository.findAll().stream()
                .map(assetMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AssetDTO getAssetById(Long id) {
        return assetMapper.toDTO(trova(id));
    }

    @Override
    @Transactional
    public String updateAsset(AssetDTO dto) {
        if (dto.getCodice() == null || dto.getCodice().isBlank()
                || dto.getNome() == null || dto.getNome().isBlank()
                || dto.getCriticitaDiDefault() == null) {
            throw new IllegalArgumentException(
                    "Impossibile salvare il tipo asset: valorizzare codice, nome e criticita di default");
        }

        String codice = dto.getCodice().trim();
        dto.setCodice(codice);
        dto.setNome(dto.getNome().trim());

        if (dto.getId() != null) {
            if (tipoAssetRepository.existsByCodiceIgnoreCaseAndIdNot(codice, dto.getId())) {
                throw new ConflictException("Esiste gia' un tipo asset con codice '" + codice + "'");
            }
            TipoAsset esistente = trova(dto.getId().longValue());
            assetMapper.apply(dto, esistente);
            tipoAssetRepository.save(esistente);
            return "Asset modificato con successo";
        }

        if (tipoAssetRepository.existsByCodiceIgnoreCase(codice)) {
            throw new ConflictException("Esiste gia' un tipo asset con codice '" + codice + "'");
        }
        tipoAssetRepository.save(assetMapper.toEntity(dto));
        return "Asset aggiunto con successo!";
    }

    @Override
    @Transactional
    public String delete(Long id) {
        TipoAsset tipoAsset = trova(id);
        tipoAssetRepository.delete(tipoAsset);
        return "Asset eliminato con successo.";
    }

    private TipoAsset trova(Long id) {
        return tipoAssetRepository.findById(id.intValue())
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}

package com.aether.backend.service.impl;

import com.aether.backend.service.AssetService;
import com.aether.backend.dto.AssetDTO;
import com.aether.backend.entity.Asset;
import com.aether.backend.mapper.AssetMapper;
import com.aether.backend.repository.AssetRepository;
import com.aether.backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    @Override
    public List<AssetDTO> getAllAssets() {
        return assetRepository.findAll().stream()
                .map(assetMapper::toDTO)
                .collect(Collectors.toList());
    }



    @Override
    public AssetDTO getAssetById(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset non trovato con id: " + id));

        return assetMapper.toDTO(asset);
    }

    @Override
    public String updateAsset(AssetDTO dto) {
        String msg="";

        if (dto.getId_tipo_asset()!=null) {
            Asset asset = assetMapper.toEntity(dto);
            assetRepository.save(asset);
            return "Asset modificato con successo";
        }
        else {
                if (dto.getNome() != null && dto.getCodice() != null &&
                        dto.getCriticita_di_default() != null ) {
                    Asset a = AssetMapper.toEntity(dto);
                    assetRepository.save(a);
                    msg = "Asset aggiunto con successo!";
                } else {
                    msg = "Impossibile aggiungere asset: valorizzare tutti i campi con valori validi !";
                }

            } return msg;

    }

    @Override
    public String delete(Long id) {
        if(assetRepository.existsById(id)){
            assetRepository.deleteById(id);
            return "Asset eliminato con successo.";
        } else {
            return "Id Asset non presente.";
        }

    }
}
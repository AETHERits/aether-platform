package com.aether.backend.asset.service;

import com.aether.backend.asset.dto.AssetDTO;
import com.aether.backend.asset.entity.Asset;
import com.aether.backend.asset.mapper.AssetMapper;
import com.aether.backend.asset.repository.AssetRepository;
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
    public AssetDTO createAsset(AssetDTO dto) {
        Asset asset = assetMapper.toEntity(dto);
        Asset saved = assetRepository.save(asset);
        return assetMapper.toDTO(saved);
    }
}
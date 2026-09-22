package com.aether.backend.asset.service;

import com.aether.backend.asset.dto.AssetDTO;
import java.util.List;

public interface AssetService {
    List<AssetDTO> getAllAssets();
    AssetDTO getAssetById(Long id);
    AssetDTO createAsset(AssetDTO dto);
}

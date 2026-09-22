package com.aether.backend.service;

import com.aether.backend.dto.AssetDTO;
import java.util.List;

public interface AssetService {
    List<AssetDTO> getAllAssets();
    AssetDTO getAssetById(Long id);
    String updateAsset(AssetDTO dto);

    String delete(Long id);
}

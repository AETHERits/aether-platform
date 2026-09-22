package com.aether.backend.mapper;

import com.aether.backend.dto.AssetDTO;
import com.aether.backend.entity.Asset;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {

    public AssetDTO toDTO(Asset asset) {
        if (asset == null) return null;

        return new AssetDTO(
                asset.getId_tipo_asset(),
                asset.getCodice(),
                asset.getNome(),
                asset.getCriticita_di_default()
        );
    }

    public static Asset toEntity(AssetDTO dto) {
        if (dto == null) return null;

        Asset asset = new Asset();
        asset.setCodice(dto.getCodice());
        asset.setNome(dto.getNome());
        asset.setCriticita_di_default(dto.getCriticita_di_default());
        return asset;
    }
}
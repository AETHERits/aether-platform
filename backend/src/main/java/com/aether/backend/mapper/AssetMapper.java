package com.aether.backend.mapper;

import com.aether.backend.dto.AssetDTO;
import com.aether.backend.entity.TipoAsset;
import org.springframework.stereotype.Component;

/**
 * Il modulo frontend "assets" gestisce il catalogo tipi_asset (codice, nome, criticita di default),
 * non gli asset tecnici fisici (tabella asset_tecnici / entity Asset).
 */
@Component
public class AssetMapper {

    public AssetDTO toDTO(TipoAsset tipoAsset) {
        if (tipoAsset == null) {
            return null;
        }
        return new AssetDTO(
                tipoAsset.getId(),
                tipoAsset.getCodice(),
                tipoAsset.getNome(),
                tipoAsset.getCriticitaDiDefault()
        );
    }

    public TipoAsset toEntity(AssetDTO dto) {
        if (dto == null) {
            return null;
        }
        TipoAsset tipoAsset = new TipoAsset();
        apply(dto, tipoAsset);
        return tipoAsset;
    }

    public void apply(AssetDTO dto, TipoAsset tipoAsset) {
        tipoAsset.setCodice(dto.getCodice());
        tipoAsset.setNome(dto.getNome());
        tipoAsset.setCriticitaDiDefault(dto.getCriticitaDiDefault());
    }
}

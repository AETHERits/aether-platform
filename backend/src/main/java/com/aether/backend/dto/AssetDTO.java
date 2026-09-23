package com.aether.backend.dto;

import com.aether.backend.entity.CriticitaAsset;
import lombok.*;

@Data
@AllArgsConstructor @NoArgsConstructor
public class AssetDTO {
    private Long id_tipo_asset;
    private String codice;
    private String nome;
    private CriticitaAsset criticita_di_default;
}
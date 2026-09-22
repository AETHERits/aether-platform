package com.aether.backend.asset.dto;

import com.aether.backend.asset.entity.Priority;
import lombok.*;

@Data
@AllArgsConstructor @NoArgsConstructor
public class AssetDTO {
    private Long id_tipo_asset;
    private String codice;
    private String nome;
    private Priority criticita_di_default;
}
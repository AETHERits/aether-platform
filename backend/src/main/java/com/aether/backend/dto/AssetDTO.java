package com.aether.backend.dto;

import com.aether.backend.entity.CriticitaAsset;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetDTO {

    @JsonProperty("id_tipo_asset")
    private Integer id;

    private String codice;

    private String nome;

    @JsonProperty("criticita_di_default")
    private CriticitaAsset criticitaDiDefault;
}

package com.aether.backend.mapper;

import com.aether.backend.dto.ColoniaResponse;
import com.aether.backend.entity.Colonia;

public class ColoniaMapper {

    public static ColoniaResponse toDTO(Colonia entity) {
        if (entity == null) {
            return null;
        }
        return new ColoniaResponse(
                entity.getCodice(),
                entity.getNome(),
                entity.getStatoOperativoColonia()
        );
    }
}
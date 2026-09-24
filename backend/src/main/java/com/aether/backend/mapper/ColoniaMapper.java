package com.aether.backend.mapper;

import com.aether.backend.dto.ColoniaResponse;
import com.aether.backend.entity.Colonia;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ColoniaMapper {

    public ColoniaResponse toDTO(Colonia entity) {
        if (entity == null) {
            return null;
        }

        ColoniaResponse dto = new ColoniaResponse();
        dto.setCodice(entity.getCodice());
        dto.setNome(entity.getNome());

        // Unisce latitudine e longitudine in un'unica stringa "lat, long"
        if (entity.getLatitudine() != null && entity.getLongitudine() != null) {
            dto.setCoordinate(entity.getLatitudine() + ", " + entity.getLongitudine());
        }

        dto.setStatoOperativoColonia(entity.getStatoOperativoColonia());

        // Utilizza getCancellato() invece di isCancellato()
        dto.setCancellato(entity.getCancellato());

        return dto;
    }

    public Colonia toEntity(ColoniaResponse dto) {
        if (dto == null) {
            return null;
        }

        Colonia entity = new Colonia();
        entity.setCodice(dto.getCodice());
        entity.setNome(dto.getNome());

        parseAndSetCoordinates(dto.getCoordinate(), entity);

        entity.setStatoOperativoColonia(dto.getStatoOperativoColonia());
        if (dto.getCancellato() != null) {
            entity.setCancellato(dto.getCancellato());
        }

        return entity;
    }

    public void parseAndSetCoordinates(String coordinateStr, Colonia entity) {
        if (coordinateStr != null && !coordinateStr.isBlank()) {
            String[] parts = coordinateStr.split(",");
            if (parts.length == 2) {
                try {
                    entity.setLatitudine(new BigDecimal(parts[0].trim()));
                    entity.setLongitudine(new BigDecimal(parts[1].trim()));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid coordinate format. Expected format: 'latitude, longitude' (e.g. '14.567, -42.123')");
                }
            } else {
                throw new IllegalArgumentException("Coordinates must contain latitude and longitude separated by a comma (e.g. '14.567, -42.123')");
            }
        } else {
            entity.setLatitudine(null);
            entity.setLongitudine(null);
        }
    }
}
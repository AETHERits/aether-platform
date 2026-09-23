package com.aether.backend.dto;

import com.aether.backend.entity.Severita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NuovoIncidenteRequest(

        @NotNull Long idColonia,

        Long idMissione,

        Long idHabitat,

        Long idAsset,

        @NotBlank String titolo,

        @NotBlank String descrizione,

        @NotNull Severita severita,

        @NotNull Long riportatoDa
) {
}
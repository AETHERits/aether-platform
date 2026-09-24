package com.aether.backend.dto;

import com.aether.backend.entity.StatoOperativoColonia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO di risposta per la visualizzazione dell'elenco basi marziane (ARES-101).
 * Rispetta gli Acceptance Criteria: mostra codice, nome e stato operativo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColoniaResponse {

    @NotBlank(message = "Colony code is mandatory.")
    @Size(min = 3, max = 20, message = "Code must be between 3 and 20 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Code can only contain letters, numbers, hyphens, and underscores.")
    private String codice;

    @NotBlank(message = "Colony name is mandatory.")
    @Size(max = 100, message = "Name cannot exceed 100 characters.")
    private String nome;

    @Size(max = 100, message = "Coordinates cannot exceed 100 characters.")
    private String coordinate;

    @NotNull(message = "Operational status is mandatory.")
    @Pattern(regexp = "^(ATTIVA|INATTIVA|MANUTENZIONE|DISMESSA)$",
            message = "Status must be one of: ATTIVA, INATTIVA, MANUTENZIONE, DISMESSA.")
    private StatoOperativoColonia statoOperativoColonia;

    private Boolean cancellato; // Generates setCancellato() and getCancellato() via Lombok @Data

    public ColoniaResponse(String codice, String nome, StatoOperativoColonia statoOperativoColonia) {
        this.codice = codice;
        this.nome = nome;
        this.statoOperativoColonia = statoOperativoColonia;
    }
}
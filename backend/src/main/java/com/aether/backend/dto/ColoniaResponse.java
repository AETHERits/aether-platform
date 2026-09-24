package com.aether.backend.dto;

import com.aether.backend.entity.StatoOperativoColonia;
import jakarta.validation.constraints.NotBlank;
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

    private String codice;
    private String nome;
    private StatoOperativoColonia stato;
}
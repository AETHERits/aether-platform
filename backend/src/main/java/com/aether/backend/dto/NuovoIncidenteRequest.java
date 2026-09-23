package com.aether.backend.dto;

import com.aether.backend.entity.Severita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO di INPUT della POST /api/incidenti.
 * Contiene SOLO ciò che l'operatore può decidere tramite il form.
 *
 * Note di design [HEL-501] (importanti per chi revisa):
 * - NON esiste il campo "stato": un incidente nasce APERTO per regola
 *   di server (criterio 4). Il client non può barare;
 * - NON esiste il campo "registrato_il": il timestamp è deciso dal
 *   server (criterio 5);
 * - le annotazioni Bean Validation fanno rispondere 400 in automatico
 *   (gestito dal GlobalExceptionHandler del team) se il payload è invalido.
 *
 * È un record: immutabile e compatto, ideale per i DTO di richiesta.
 */
public record NuovoIncidenteRequest(

        /** Base/colonia in cui è avvenuto l'evento: obbligatoria (criterio 1). */
        @NotNull Long idColonia,

        /** Missione coinvolta (opzionale). */
        Long idMissione,

        /** Habitat coinvolto (opzionale). */
        Long idHabitat,

        /** Asset coinvolto (opzionale). */
        Long idAsset,

        /** Titolo sintetico: obbligatorio, non solo spazi (criterio 2). */
        @NotBlank String titolo,

        /** Descrizione dettagliata: obbligatoria, non solo spazi (criterio 2). */
        @NotBlank String descrizione,

        /** Severità: obbligatoria, ammesso solo un valore dell'enum (criterio 3). */
        @NotNull Severita severita,

        /** Operatore segnalante: oggi placeholder, domani dal login. */
        @NotNull Long riportatoDa
) {
}
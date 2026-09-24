package com.aether.backend.dto;

/**
 * Payload del PUT /api/missioni/{id}: sostituzione completa dei dati modificabili.
 * Ha gli stessi campi e le stesse validazioni di {@link CreaMissioneRequest}.
 * Lo stato NON e' modificabile da qui: si usa PATCH /api/missioni/{id}/stato.
 */

public class AggiornaMissioneRequest extends CreaMissioneRequest{
}

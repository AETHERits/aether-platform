package com.aether.backend.incidenti;

/**
 * Severità di un incidente operativo.
 *
 * Attenzione: i valori vengono salvati in DB come stringhe maiuscole
 * (vedi @Enumerated(EnumType.STRING) sull'entity Incidente).
 * Non rinominare le costanti senza allineare la CHECK constraint sul DB.
 *
 * [HEL-501] Criterio 3 "Severità valida": il form offre al cliente
 * soltanto questi valori, quindi un valore invalido non è selezionabile.
 */
public enum Severita {
    BASSA,
    MEDIA,
    ALTA,
    CRITICA
}
package com.aether.backend.incidenti;

/**
 * Stati del ciclo di vita di un incidente.
 *
 * [HEL-501] Criterio 4 "Stato iniziale OPEN": ogni nuovo incidente nasce
 * nello stato APERTO per decisione del SERVER (vedi IncidenteService).
 * Gli altri stati entreranno in gioco con le story di gestione/chiusura.
 */
public enum StatoIncidente {
    APERTO,
    IN_GESTIONE,
    CHIUSO
}
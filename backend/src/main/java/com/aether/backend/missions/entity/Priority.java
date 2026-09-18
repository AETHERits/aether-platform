package com.example.missions.entity;

/**
 * Priorità della missione. Essendo un enum, i valori ammessi sono
 * intrinsecamente controllati: qualsiasi valore diverso viene rifiutato
 * automaticamente in fase di deserializzazione JSON con un errore comprensibile.
 */
public enum Priority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

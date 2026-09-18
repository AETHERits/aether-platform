package com.example.missions.entity;

/**
 * Stato del ciclo di vita di una missione.
 * Ogni missione viene creata SEMPRE in stato DRAFT: non è possibile impostare
 * uno stato diverso in fase di creazione (vedi acceptance criteria).
 */
public enum MissionStatus {
    DRAFT,
    APPROVED,
    IN_PROGRESS,
    COMPLETED,
    ABORTED
}

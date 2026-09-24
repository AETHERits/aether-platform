package com.aether.backend.entity;

import java.util.EnumSet;
import java.util.Set;

/**
 * Stati del ciclo di vita di una missione, con le transizioni consentite.
 *
 * <pre>
 * DRAFT ──► PLANNED ──► APPROVED ──► IN_PROGRESS ──► COMPLETED
 *   ▲          │                       │    ▲
 *   └──────────┘                       ▼    │
 *                                   SUSPENDED
 *
 * CANCELLED raggiungibile da tutti gli stati non finali.
 * COMPLETED e CANCELLED sono stati finali.
 * </pre>
 */
public enum StatoMissione {
    DRAFT,
    PLANNED,
    APPROVED,
    IN_PROGRESS,
    SUSPENDED,
    COMPLETED,
    CANCELLED;

    public Set<StatoMissione> transizioniConsentite() {
        return switch (this) {
            case DRAFT -> EnumSet.of(PLANNED, CANCELLED);
            case PLANNED -> EnumSet.of(DRAFT, APPROVED, CANCELLED);
            case APPROVED -> EnumSet.of(IN_PROGRESS, CANCELLED);
            case IN_PROGRESS -> EnumSet.of(SUSPENDED, COMPLETED, CANCELLED);
            case SUSPENDED -> EnumSet.of(IN_PROGRESS, CANCELLED);
            case COMPLETED, CANCELLED -> EnumSet.noneOf(StatoMissione.class);
        };
    }

    public boolean puoTransitareA(StatoMissione destinazione) {
        return transizioniConsentite().contains(destinazione);
    }
}

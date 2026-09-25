package com.aether.backend.mapper;

import com.aether.backend.entity.LivelloSicurezza;
import com.aether.backend.entity.Priorita;
import com.aether.backend.entity.Rischio;

public final class MissionApiMapper {

    private MissionApiMapper() {
    }

    public static Priorita parsePriorita(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("La priorita' e' obbligatoria");
        }
        return switch (raw.trim().toUpperCase()) {
            case "LOW", "BASSA" -> Priorita.BASSA;
            case "MEDIUM", "MEDIA" -> Priorita.MEDIA;
            case "HIGH", "ALTA" -> Priorita.ALTA;
            case "CRITICAL", "CRITICA" -> Priorita.CRITICA;
            default -> throw new IllegalArgumentException("Priorita' non ammessa: " + raw);
        };
    }

    public static String toFrontend(Priorita priorita) {
        if (priorita == null) {
            return null;
        }
        return switch (priorita) {
            case BASSA -> "LOW";
            case MEDIA -> "MEDIUM";
            case ALTA -> "HIGH";
            case CRITICA -> "CRITICAL";
        };
    }

    public static LivelloSicurezza parseLivello(String raw) {
        if (raw == null || raw.isBlank()) {
            return LivelloSicurezza.STANDARD;
        }
        return switch (raw.trim().toUpperCase()) {
            case "STANDARD" -> LivelloSicurezza.STANDARD;
            case "ELEVATED", "ELEVATO" -> LivelloSicurezza.ELEVATO;
            case "HIGH_RISK", "ALTO_RISCHIO" -> LivelloSicurezza.ALTO_RISCHIO;
            case "CRITICAL", "CRITICO" -> LivelloSicurezza.CRITICO;
            default -> throw new IllegalArgumentException("Livello di sicurezza non ammesso: " + raw);
        };
    }

    public static String toFrontend(LivelloSicurezza livello) {
        if (livello == null) {
            return "STANDARD";
        }
        return switch (livello) {
            case STANDARD -> "STANDARD";
            case ELEVATO -> "ELEVATED";
            case ALTO_RISCHIO -> "HIGH_RISK";
            case CRITICO -> "CRITICAL";
        };
    }

    public static Rischio rischioDaLivello(LivelloSicurezza livello) {
        return switch (livello == null ? LivelloSicurezza.STANDARD : livello) {
            case STANDARD -> Rischio.BASSO;
            case ELEVATO -> Rischio.MEDIO;
            case ALTO_RISCHIO, CRITICO -> Rischio.ALTO;
        };
    }

    public static Rischio parseRischio(String raw, LivelloSicurezza livello) {
        if (raw == null || raw.isBlank()) {
            return rischioDaLivello(livello);
        }
        return switch (raw.trim().toUpperCase()) {
            case "BASSO", "LOW" -> Rischio.BASSO;
            case "MEDIO", "MEDIUM" -> Rischio.MEDIO;
            case "ALTO", "HIGH" -> Rischio.ALTO;
            default -> throw new IllegalArgumentException("Rischio non ammesso: " + raw);
        };
    }

    public static String tipologiaDb(String missionType) {
        if (missionType == null || missionType.isBlank()) {
            throw new IllegalArgumentException("Il tipo di missione e' obbligatorio");
        }
        return switch (missionType.trim().toUpperCase()) {
            case "EXPLORATION", "INTERNAL", "EVA", "INSPECTION", "TRANSPORT", "ESPLORAZIONE" -> "ESPLORAZIONE";
            case "MAINTENANCE", "MANUTENZIONE" -> "MANUTENZIONE";
            case "LOGISTICS", "LOGISTICA" -> "LOGISTICA";
            case "SCIENCE", "SCIENZA" -> "SCIENZA";
            case "RESCUE", "SOCCORSO" -> "SOCCORSO";
            default -> missionType.trim().toUpperCase();
        };
    }

    public static String toFrontendType(String nomeDb) {
        if (nomeDb == null) {
            return "UNKNOWN";
        }
        return switch (nomeDb.toUpperCase()) {
            case "ESPLORAZIONE" -> "EXPLORATION";
            case "MANUTENZIONE" -> "MAINTENANCE";
            case "LOGISTICA" -> "LOGISTICS";
            case "SCIENZA" -> "SCIENCE";
            case "SOCCORSO" -> "RESCUE";
            default -> nomeDb;
        };
    }
}

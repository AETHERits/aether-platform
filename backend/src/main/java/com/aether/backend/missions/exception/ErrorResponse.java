package com.example.missions.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Formato uniforme di risposta di errore per tutte le API.
 * "fieldErrors" viene valorizzato solo per errori di validazione campo-per-campo,
 * così il client (frontend, altro servizio, ecc.) riceve un messaggio comprensibile
 * e sa esattamente quale campo correggere.
 */
@Getter
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<Map<String, String>> fieldErrors;
}

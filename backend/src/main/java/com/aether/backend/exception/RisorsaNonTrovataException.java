package com.aether.backend.exception;

public class RisorsaNonTrovataException extends RuntimeException {

    public RisorsaNonTrovataException(Long id) {
        super("Nessuna risorsa trovata con id " + id);
    }
}

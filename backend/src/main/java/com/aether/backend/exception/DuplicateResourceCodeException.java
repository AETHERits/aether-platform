package com.aether.backend.exception;

public class DuplicateResourceCodeException extends ConflictException {
    public DuplicateResourceCodeException(String codice) {
        super("Esiste gia' una risorsa con codice '" + codice + "'");
    }
}

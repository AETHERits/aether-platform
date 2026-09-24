package com.aether.backend.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long id) {
        super("Nessuna risorsa trovata con id " + id);
    }
}

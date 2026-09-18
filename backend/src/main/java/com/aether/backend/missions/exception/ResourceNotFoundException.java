package com.example.missions.exception;

/** Sollevata quando una risorsa referenziata (es. base/colonia) non esiste. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

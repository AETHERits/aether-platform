package com.example.missions.exception;

/** Sollevata quando si tenta di creare una risorsa con un codice/chiave già esistente. */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}

package com.aether.backend.exception;

public class CodiceRisorsaDuplicatoException extends RuntimeException {

    public CodiceRisorsaDuplicatoException(String codice) {
        super("Esiste già una risorsa con codice '" + codice + "'");
    }
}

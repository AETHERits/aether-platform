package com.example.missions.exception;

/**
 * Sollevata quando i dati sono formalmente validi (tipi, formati) ma violano
 * una regola di business, es. finestra temporale incoerente (data fine prima
 * della data inizio, missione già nel passato, ecc.).
 */
public class BusinessRuleViolationException extends RuntimeException {
    public BusinessRuleViolationException(String message) {
        super(message);
    }
}

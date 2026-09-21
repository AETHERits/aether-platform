package com.aether.backend.colonie;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity JPA READ-ONLY mappata sulla tabella ESISTENTE "colonie".
 *
 * Serve unicamente a fornire l'elenco delle basi alla select "Base"
 * del form frontend (criterio 1: base obbligatoria ma scegliibile
 * solo tra quelle esistenti).
 *
 * Questa feature NON deve mai creare/modificare/cancellare colonie:
 * per questo l'entity espone SOLO i getter, senza setter,
 * e il repository viene usato solo con findAll().
 */
@Entity
@Table(name = "colonie")
public class Colonia {

    /** Chiave primaria della tabella colonie. */
    @Id
    private Long id;

    /** Nome visualizzato nella select del form (es. "Ares Prime"). */
    private String nome;

    public Long getId() { return id; }
    public String getNome() { return nome; }
}
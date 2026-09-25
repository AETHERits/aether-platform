package com.aether.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Mappatura PARZIALE della tabella utenti: serve come riferimento per le relazioni
 * (riportato_da, approvato_da, chiuso_da...). password_hash, stato e gli altri campi di
 * sicurezza NON sono mappati di proposito: quando si costruira' il modulo di autenticazione
 * vanno aggiunti (e save() oggi fallirebbe perche' password_hash e' NOT NULL).
 */
@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    // 1:1 -> astronauti (UNIQUE su id_astronauta); un utente puo' non avere un astronauta (es. admin)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_astronauta")
    private Astronauta astronauta;
}


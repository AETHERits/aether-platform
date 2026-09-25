package com.aether.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "habitat")
@Getter
@Setter
@NoArgsConstructor
public class Habitat extends Auditable {

    // id_habitat e' SERIAL (int4) -> Integer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habitat")
    private Integer id;

    // N:1 -> colonie
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_colonia", nullable = false)
    private Colonia colonia;

    // relazione ricorsiva: un habitat puo' contenere altri habitat
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_habitat_padre")
    private Habitat habitatPadre;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String funzione;

    private Integer capacita;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatoHabitat stato;

    @Column(nullable = false)
    private boolean cancellato = false;
}

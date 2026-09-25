package com.aether.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "astronauti")
@Getter
@Setter
@NoArgsConstructor
public class Astronauta extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_astronauta")
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String matricola;

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(nullable = false, length = 60)
    private String cognome;

    @Column(name = "data_di_nascita")
    private LocalDate dataDiNascita;

    @Column(nullable = false, length = 50)
    private String mansione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_colonia")
    private Colonia colonia;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_servizio", nullable = false, length = 20)
    private StatoServizio statoServizio;

    @Column(nullable = false)
    private boolean cancellato = false;
}


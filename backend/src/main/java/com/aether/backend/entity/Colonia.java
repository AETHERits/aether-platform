package com.aether.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "colonie")
@Getter
@Setter
@NoArgsConstructor
public class Colonia extends Auditable {

    // id_colonia e' SERIAL (int4) -> Integer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colonia")
    private Integer id;

    @Column(nullable = false, unique = true, length = 10)
    private String codice;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 100)
    private String area;

    @Column(precision = 9, scale = 6)
    private BigDecimal latitudine;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitudine;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_operativo", nullable = false, length = 20)
    private StatoOperativoColonia statoOperativo;

    // N:1 -> astronauti. La FK e' circolare (astronauti.id_colonia <-> colonie.id_responsabile)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_responsabile")
    private Astronauta responsabile;

    @Column(name = "capacita_massima")
    private Integer capacitaMassima;

    @Column(nullable = false)
    private boolean cancellato = false;
}


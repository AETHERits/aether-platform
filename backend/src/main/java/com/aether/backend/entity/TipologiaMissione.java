package com.aether.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipologie_missione")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipologiaMissione {

    // id_tipologia e' SERIAL (int4) -> Integer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipologia")
    private Integer id;

    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String tipo;

    @Column(name = "richiede_approvazione", nullable = false)
    private boolean richiedeApprovazione = false;
}


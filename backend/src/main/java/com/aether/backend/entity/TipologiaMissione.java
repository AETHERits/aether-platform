package com.aether.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipologie_missione")
public class TipologiaMissione {

    @Id
    @Column(name = "id_tipologia")
    private Integer id;
}

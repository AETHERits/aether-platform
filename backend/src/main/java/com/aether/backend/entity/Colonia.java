package com.aether.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "colonie")
public class Colonia {

    @Id
    @Column(name = "id_colonia")
    private Integer id;
}

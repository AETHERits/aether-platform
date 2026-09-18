package com.aether.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "astronauti")
public class Astronauta {

    @Id
    @Column(name = "id_astronauta")
    private Long id;
}

package com.aether.backend.colonie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "colonie")
public class Colonia {

    @Id
    @Column(name = "id_colonia")
    private Long id;

    private String nome;

    public Long getId() { return id; }
    public String getNome() { return nome; }
}
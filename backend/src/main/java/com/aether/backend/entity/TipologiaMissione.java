package com.aether.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Id
    @Column(name = "id_tipologia")
    private Long id;
    
    @Column(name = "nome", nullable = false, unique = true)
    private String tipo;
}

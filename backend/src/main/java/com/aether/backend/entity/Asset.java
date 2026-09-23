package com.aether.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "tipi_asset")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id_tipo_asset;

    @Column(unique= true, nullable= false)
    private String codice;

    @Column(nullable= false)
    private String nome;

    @Enumerated(EnumType.STRING)
    private CriticitaAsset criticita_di_default;
}
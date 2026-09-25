package com.aether.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipi_asset")
@Getter
@Setter
@NoArgsConstructor
public class TipoAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_asset")
    private Integer id;

    @Column(nullable = false, unique = true, length = 30)
    private String codice;

    @Column(nullable = false, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticita_di_default", length = 20)
    private CriticitaAsset criticitaDiDefault;
}

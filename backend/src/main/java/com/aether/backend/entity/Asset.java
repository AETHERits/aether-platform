package com.aether.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "asset_tecnici")
@Getter
@Setter
@NoArgsConstructor
public class Asset extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset")
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_colonia", nullable = false)
    private Colonia colonia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_habitat")
    private Habitat habitat;

    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_asset", nullable = false)
    private TipoAsset tipoAsset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CriticitaAsset criticita;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatoAsset stato;

    @Column(nullable = false)
    private boolean cancellato = false;

    @Column(name = "numero_seriale", unique = true, length = 120)
    private String numeroSeriale;

    @Column(length = 120)
    private String produttore;

    @Column(length = 120)
    private String modello;

    @Column(name = "ore_di_funzionamento", nullable = false, precision = 14, scale = 2)
    private BigDecimal oreDiFunzionamento = BigDecimal.ZERO;
}

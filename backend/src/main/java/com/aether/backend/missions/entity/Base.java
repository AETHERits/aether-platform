package com.example.missions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rappresenta una base/colonia esistente a cui una missione può essere assegnata.
 * In un sistema reale questa entità sarebbe probabilmente gestita da un modulo separato;
 * qui è inclusa in forma minimale per poter validare "obiettivo e base/colonia esistente".
 */
@Entity
@Table(name = "bases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String location;

}

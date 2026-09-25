package com.aether.backend.repository;

import com.aether.backend.entity.TipologiaMissione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipologiaMissioneRepository extends JpaRepository<TipologiaMissione, Integer> {

    Optional<TipologiaMissione> findByTipoIgnoreCase(String tipo);
}

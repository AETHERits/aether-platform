package com.aether.backend.repository;

import com.aether.backend.entity.Missione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissioneRepository extends JpaRepository<Missione, Long> {

    boolean existsByCodice(String codice);
}

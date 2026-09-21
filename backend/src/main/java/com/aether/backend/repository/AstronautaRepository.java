package com.aether.backend.repository;

import com.aether.backend.entity.Astronauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AstronautaRepository extends JpaRepository<Astronauta, Long> {
}

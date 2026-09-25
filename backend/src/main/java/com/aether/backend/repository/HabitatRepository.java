package com.aether.backend.repository;

import com.aether.backend.entity.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitatRepository extends JpaRepository<Habitat, Integer> {
}

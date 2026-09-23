package com.aether.backend.repository;

import com.aether.backend.entity.Colonia;
import org.springframework.data.jpa.repository      .JpaRepository;

public interface ColoniaRepository extends JpaRepository<Colonia, Long> {
}
package com.aether.backend.repository;

import com.aether.backend.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
}

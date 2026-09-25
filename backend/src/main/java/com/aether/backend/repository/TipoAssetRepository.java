package com.aether.backend.repository;

import com.aether.backend.entity.TipoAsset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoAssetRepository extends JpaRepository<TipoAsset, Integer> {

    boolean existsByCodiceIgnoreCase(String codice);

    boolean existsByCodiceIgnoreCaseAndIdNot(String codice, Integer id);
}

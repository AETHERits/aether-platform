package com.aether.backend.controller;

import com.aether.backend.dto.AssetDTO;
import com.aether.backend.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {


    private final AssetService assetService;

    @GetMapping
    public ResponseEntity<List<AssetDTO>> getAll() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAssetById(id));
    }

    @PostMapping(value = "/aggiungi-asset", produces = MediaType.TEXT_PLAIN_VALUE)
    public String update(@RequestBody AssetDTO dto) {
        return assetService.updateAsset(dto);
    }

    @DeleteMapping(value = "/cancella/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String delete(@PathVariable Long id) {
        return assetService.delete(id);
    }

}

package com.aether.backend.controller;

import com.aether.backend.dto.ColoniaResponse;
import com.aether.backend.service.ColoniaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colonie")
public class ColoniaController {

    private final ColoniaService coloniaService;

    public ColoniaController(ColoniaService coloniaService) {
        this.coloniaService = coloniaService;
    }

    // GET /api/colonie - Retrieve all non-deleted colonies
    @GetMapping
    public ResponseEntity<List<ColoniaResponse>> getAllColonie() {
        List<ColoniaResponse> colonie = coloniaService.findAll();
        return ResponseEntity.ok(colonie);
    }

    // GET /api/colonie/{id} - Retrieve a specific colony by ID
    @GetMapping("/{id}")
    public ResponseEntity<ColoniaResponse> getColoniaById(@PathVariable Long id) {
        ColoniaResponse colonia = coloniaService.findById(id);
        return ResponseEntity.ok(colonia);
    }

    // POST /api/colonie - Create a new colony
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MISSION_CONTROLLER')")
    public ResponseEntity<ColoniaResponse> createColonia(@Valid @RequestBody ColoniaResponse dto) {
        ColoniaResponse created = coloniaService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // PUT /api/colonie/{id} - Full update of an existing colony
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COLONY_COMMANDER')")
    public ResponseEntity<ColoniaResponse> updateColonia(@PathVariable Long id, @Valid @RequestBody ColoniaResponse dto) {
        ColoniaResponse updated = coloniaService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // PATCH /api/colonie/{id} - Partial update of an existing colony (@Valid is omitted to allow partial payloads)
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COLONY_COMMANDER')")
    public ResponseEntity<ColoniaResponse> patchColonia(@PathVariable Long id, @RequestBody ColoniaResponse dto) {
        ColoniaResponse patched = coloniaService.patch(id, dto);
        return ResponseEntity.ok(patched);
    }

    // DELETE /api/colonie/{id} - Soft delete a colony (BR-010)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteColonia(@PathVariable Long id) {
        coloniaService.deleteOrDeactivate(id);
        return ResponseEntity.noContent().build();
    }
}
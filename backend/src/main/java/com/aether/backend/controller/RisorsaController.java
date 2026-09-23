package com.aether.backend.controller;

import com.aether.backend.dto.RisorsaCreateRequest;
import com.aether.backend.dto.RisorsaResponse;
import com.aether.backend.dto.RisorsaUpdateRequest;
import com.aether.backend.service.RisorsaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/risorse")
public class RisorsaController {

    private final RisorsaService risorsaService;

    public RisorsaController(RisorsaService risorsaService) {
        this.risorsaService = risorsaService;
    }

    @PostMapping
    public ResponseEntity<RisorsaResponse> crea(@Valid @RequestBody RisorsaCreateRequest request) {
        RisorsaResponse creata = risorsaService.crea(request);
        URI location = URI.create("/api/risorse/" + creata.getIdRisorsa());
        return ResponseEntity.created(location).body(creata);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RisorsaResponse> aggiorna(@PathVariable Long id,
                                                     @Valid @RequestBody RisorsaUpdateRequest request) {
        return ResponseEntity.ok(risorsaService.aggiorna(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RisorsaResponse> trovaPerId(@PathVariable Long id) {
        return ResponseEntity.ok(risorsaService.trovaPerId(id));
    }

    @GetMapping
    public ResponseEntity<List<RisorsaResponse>> elenca(
            @RequestParam(required = false) Boolean attivo) {
        return ResponseEntity.ok(risorsaService.elenca(attivo));
    }

    @PatchMapping("/{id}/attiva")
    public ResponseEntity<RisorsaResponse> attiva(@PathVariable Long id) {
        return ResponseEntity.ok(risorsaService.attiva(id));
    }

    @PatchMapping("/{id}/disattiva")
    public ResponseEntity<RisorsaResponse> disattiva(@PathVariable Long id) {
        return ResponseEntity.ok(risorsaService.disattiva(id));
    }
}

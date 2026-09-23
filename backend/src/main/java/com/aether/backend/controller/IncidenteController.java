package com.aether.backend.controller;

import com.aether.backend.entity.Colonia;
import com.aether.backend.entity.Incidente;
import com.aether.backend.service.IncidenteService;
import com.aether.backend.repository.ColoniaRepository;
import com.aether.backend.dto.NuovoIncidenteRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IncidenteController {

    private final IncidenteService service;
    private final ColoniaRepository colonie;

    public IncidenteController(IncidenteService service, ColoniaRepository colonie) {
        this.service = service;
        this.colonie = colonie;
    }

    @GetMapping("/colonie")
    public List<Colonia> listaColonie() {
        return colonie.findAll();
    }

    @PostMapping("/incidenti")
    public ResponseEntity<Incidente> crea(@Valid @RequestBody NuovoIncidenteRequest req) {
        Incidente creato = service.registra(req);
        return ResponseEntity
                .created(URI.create("/api/incidenti/" + creato.getId()))
                .body(creato);
    }

    @GetMapping("/incidenti")
    public ResponseEntity<List<Incidente>> getAll() {
        return ResponseEntity.ok(service.ottieniTutti());
    }
}
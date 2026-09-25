package com.aether.backend.controller;

import com.aether.backend.entity.Colonia;
import com.aether.backend.entity.Incidente;
import com.aether.backend.service.impl.IncidenteService;
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

/**
 * Controller REST: è il CONTRATTO con il frontend Angular [HEL-501].
 *
 * Il service Angular (incidenti.service.ts) chiama esattamente:
 *   GET  /api/colonie   -> elenco basi per la select "Base" (criterio 1)
 *   POST /api/incidenti -> registrazione incidente (criteri 1-5)
 *
 * ATTENZIONE: se cambi URL o nomi dei campi JSON, il form smette
 * di funzionare. Ogni modifica qui va riflessa nel service Angular.
 */
@RestController
@RequestMapping("/api")
public class IncidenteController {

    private final IncidenteService service;
    private final ColoniaRepository colonie;

    /** Injection delle dipendenze (constructor injection). */
    public IncidenteController(IncidenteService service, ColoniaRepository colonie) {
        this.service = service;
        this.colonie = colonie;
    }

    /**
     * Registra un nuovo incidente.
     *
     * @Valid attiva il Bean Validation sul DTO: payload invalido -> 400
     * (gestito dal GlobalExceptionHandler del team).
     * Risposta 201 Created con l'incidente completo (codice, stato APERTO,
     * timestamp): il form lo usa per il messaggio di conferma.
     */
    @PostMapping("/incidenti")
    public ResponseEntity<Incidente> crea(@Valid @RequestBody NuovoIncidenteRequest req) {
        Incidente creato = service.registra(req);
        return ResponseEntity
                .created(URI.create("/api/incidenti/" + creato.getId()))
                .body(creato);
    }

    /**
     * Recupera l'elenco di tutti gli incidenti.
     */
    @GetMapping("/incidenti")
    public ResponseEntity<List<Incidente>> getAll() {
        return ResponseEntity.ok(service.ottieniTutti());
    }
}
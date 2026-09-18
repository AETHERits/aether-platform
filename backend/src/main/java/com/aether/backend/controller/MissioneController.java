package com.aether.backend.controller;

import com.aether.backend.dto.CreaMissioneRequest;
import com.aether.backend.dto.MissioneResponse;
import com.aether.backend.service.MissioneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/missioni")
@Tag(name = "Missioni", description = "Gestione del ciclo di vita delle missioni")
public class MissioneController {

    private final MissioneService missioneService;

    public MissioneController(MissioneService missioneService) {
        this.missioneService = missioneService;
    }

    @Operation(summary = "Crea una nuova missione in stato DRAFT",
            description = "Crea una bozza di missione da cui iniziare la pianificazione. "
                    + "Lo stato iniziale e' sempre DRAFT indipendentemente da quanto inviato dal client.")
    @PostMapping
    public ResponseEntity<MissioneResponse> creaBozza(@Valid @RequestBody CreaMissioneRequest request) {
        MissioneResponse response = missioneService.creaBozza(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Recupera l'elenco di tutte le missioni")
    @GetMapping
    public ResponseEntity<List<MissioneResponse>> getAll() {
        return ResponseEntity.ok(missioneService.getAll());
    }

    @Operation(summary = "Recupera una missione tramite id")
    @GetMapping("/{id}")
    public ResponseEntity<MissioneResponse> getById(@PathVariable Long id) {
        MissioneResponse response = missioneService.getById(id);
        return ResponseEntity.ok(response);
    }
}

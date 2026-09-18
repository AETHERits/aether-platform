package com.example.missions.controller;

import com.example.missions.dto.MissionCreateRequest;
import com.example.missions.dto.MissionResponse;
import com.example.missions.service.MissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController     //questa classe gestisce richieste HTTP e che i valori restituiti dai metodi vanno convertiti automaticamente in JSON
@RequestMapping("/api/missions")  //fissa il prefisso comune per tutti gli endpoint della classe: ogni metodo qui dentro risponderà a un URL che comincia con /api/missions
@RequiredArgsConstructor  //è di Lombok: genera automaticamente un costruttore con tutti i campi final della classe come parametri. Qui c'è un solo campo final (missionService)
public class MissionController {
    
//Spring userà questo costruttore per iniettare l'implementazione di MissionService automaticamente (dependency injection), senza che tu debba scrivere new MissionServiceImpl() da nessuna parte.
    private final MissionService missionService; 

    @PostMapping
    public ResponseEntity<MissionResponse> createMission(@Valid @RequestBody MissionCreateRequest request) {
        MissionResponse created = missionService.createMission(request);
        return ResponseEntity
                .created(URI.create("/api/missions/" + created.getMissionCode()))
                .body(created);
    }

    @GetMapping("/{missionCode}")
    public ResponseEntity<MissionResponse> getMission(@PathVariable String missionCode) {
        return ResponseEntity.ok(missionService.getMissionByCode(missionCode));
    }

    @GetMapping
    public ResponseEntity<List<MissionResponse>> getAllMissions() {
        return ResponseEntity.ok(missionService.getAllMissions());
    }
}

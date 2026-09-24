
package com.aether.backend.controller;

import com.aether.backend.dto.AggiornaMissioneRequest;
import com.aether.backend.dto.CambiaStatoRequest;
import com.aether.backend.dto.CreaMissioneRequest;
import com.aether.backend.dto.ErrorResponse;
import com.aether.backend.dto.MissioneResponse;
import com.aether.backend.service.MissioneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Missione creata (header Location valorizzato)"),
            @ApiResponse(responseCode = "400", description = "Dati non validi",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Colonia, tipologia o responsabile inesistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Codice missione gia' esistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<MissioneResponse> creaBozza(@Valid @RequestBody CreaMissioneRequest request) {
        MissioneResponse response = missioneService.creaBozza(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Recupera l'elenco di tutte le missioni")
    @GetMapping
    public ResponseEntity<List<MissioneResponse>> getAll() {
        return ResponseEntity.ok(missioneService.getAll());
    }

    @Operation(summary = "Recupera una missione tramite id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Missione trovata"),
            @ApiResponse(responseCode = "400", description = "Id non valido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Missione inesistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MissioneResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(missioneService.getById(id));
    }

    @Operation(summary = "Modifica completa di una missione",
            description = "Sostituisce tutti i dati modificabili. Consentito solo se la missione e' in stato DRAFT. "
                    + "Lo stato non si cambia da qui: usare PATCH /{id}/stato.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Missione aggiornata"),
            @ApiResponse(responseCode = "400", description = "Dati non validi",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Missione, colonia, tipologia o responsabile inesistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Missione non in DRAFT o codice gia' usato",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MissioneResponse> aggiorna(@PathVariable Long id,
                                                     @Valid @RequestBody AggiornaMissioneRequest request) {
        return ResponseEntity.ok(missioneService.aggiorna(id, request));
    }

    @Operation(summary = "Cambia lo stato di una missione",
            description = "Applica una transizione del ciclo di vita: "
                    + "DRAFT -> PLANNED -> APPROVED -> IN_PROGRESS -> COMPLETED, "
                    + "con SUSPENDED (da/verso IN_PROGRESS), ritorno PLANNED -> DRAFT e CANCELLED da ogni stato non finale.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stato aggiornato"),
            @ApiResponse(responseCode = "400", description = "Stato mancante o non ammesso",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Missione inesistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Transizione non consentita",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}/stato")
    public ResponseEntity<MissioneResponse> cambiaStato(@PathVariable Long id,
                                                        @Valid @RequestBody CambiaStatoRequest request) {
        return ResponseEntity.ok(missioneService.cambiaStato(id, request.getStato()));
    }

    @Operation(summary = "Elimina una missione",
            description = "Eliminazione fisica, consentita solo per missioni in stato DRAFT. "
                    + "Le altre missioni vanno annullate con PATCH /{id}/stato (CANCELLED).")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Missione eliminata"),
            @ApiResponse(responseCode = "404", description = "Missione inesistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Missione non in DRAFT",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elimina(@PathVariable Long id) {
        missioneService.elimina(id);
        return ResponseEntity.noContent().build();
    }
}

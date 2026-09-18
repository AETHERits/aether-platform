package com.aether.backend.controller;

import com.aether.backend.dto.ColoniaResponse;
import com.aether.backend.service.ColoniaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
    /**
     * REST Controller per la gestione e la consultazione delle basi marziane (ARES-101).
     */
    @RestController
    @RequestMapping("/api/v1/colonies")
    @RequiredArgsConstructor
    @Tag(name = "Colonie", description = "API per la gestione delle basi e colonie marziane gestite da ARES")
    public class ColoniaController {

        private final ColoniaService  coloniaService;

        /**
         * Recupera l'elenco completo delle basi marziane registrate.
         *
         * @return ResponseEntity contenente la lista di ColonyResponseDto e status HTTP 200 OK.
         */
        @Operation(
                summary = "Recupera l'elenco delle basi marziane",
                description = "Restituisce tutte le basi marziane non cancellate logicamente. Ogni base include codice, nome e stato operativo."
        )
        @ApiResponse(
                responseCode = "200",
                description = "Elenco recuperato con successo (può essere un array vuoto [] se non ci sono basi)",
                content = @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = ColoniaResponse.class))
                )
        )
        @GetMapping
        public ResponseEntity<List<ColoniaResponse>> getAllColonies() {
            List<ColoniaResponse> colonies = coloniaService.findAll();
            return ResponseEntity.ok(colonies);
        }
    }

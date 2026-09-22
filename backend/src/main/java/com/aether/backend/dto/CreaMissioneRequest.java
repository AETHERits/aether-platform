package com.aether.backend.dto;

import com.aether.backend.entity.Priorita;
import com.aether.backend.entity.Rischio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CreaMissioneRequest {

    @NotBlank(message = "Il codice missione e' obbligatorio")
    @Size(max = 20, message = "Il codice missione non puo' superare i 20 caratteri")
    private String codice;

    @NotBlank(message = "L'obiettivo della missione e' obbligatorio")
    @Size(max = 255, message = "L'obiettivo non puo' superare i 255 caratteri")
    private String obiettivo;

    private String descrizione;

    @NotNull(message = "La colonia/base di destinazione e' obbligatoria")
    private Long idColonia;

    @NotNull(message = "La tipologia di missione e' obbligatoria")
    private Long idTipologia;

    @NotNull(message = "La data di inizio prevista e' obbligatoria")
    private OffsetDateTime dataInizioPrevista;

    @NotNull(message = "La data di fine prevista e' obbligatoria")
    private OffsetDateTime dataFinePrevista;

    @NotNull(message = "La priorita' e' obbligatoria")
    private Priorita priorita;

    @NotNull(message = "Il rischio e' obbligatorio")
    private Rischio rischio;

    @NotNull(message = "Il responsabile di missione e' obbligatorio")
    private Long idResponsabile;
}

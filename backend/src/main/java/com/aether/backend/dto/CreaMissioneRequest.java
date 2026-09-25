package com.aether.backend.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CreaMissioneRequest {

    @Size(max = 20, message = "Il codice missione non puo' superare i 20 caratteri")
    @JsonAlias("code")
    private String codice;

    @NotBlank(message = "L'obiettivo della missione e' obbligatorio")
    @Size(max = 255, message = "L'obiettivo non puo' superare i 255 caratteri")
    @JsonAlias("title")
    @JsonProperty("obiettivo")
    private String obiettivo;

    @JsonAlias("description")
    private String descrizione;

    @NotNull(message = "La colonia/base di destinazione e' obbligatoria")
    @JsonAlias({"idOriginColony", "id_colonia"})
    private Integer idColonia;

    private Integer idTipologia;

    @JsonAlias("missionType")
    private String missionType;

    @NotNull(message = "La data di inizio prevista e' obbligatoria")
    @JsonAlias("plannedStartAt")
    private OffsetDateTime dataInizioPrevista;

    @NotNull(message = "La data di fine prevista e' obbligatoria")
    @JsonAlias("plannedEndAt")
    private OffsetDateTime dataFinePrevista;

    @JsonAlias("priority")
    private String priorita;

    @JsonAlias("rischio")
    private String rischio;

    @JsonAlias("safetyLevel")
    private String safetyLevel;

    private Long idResponsabile;
}

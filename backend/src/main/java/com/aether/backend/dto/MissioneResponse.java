package com.aether.backend.dto;

import com.aether.backend.entity.LivelloSicurezza;
import com.aether.backend.entity.Missione;
import com.aether.backend.entity.StatoMissione;
import com.aether.backend.entity.TipologiaMissione;
import com.aether.backend.mapper.MissionApiMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class MissioneResponse {

    @JsonProperty("idMission")
    private Long id;

    @JsonProperty("code")
    private String codice;

    @JsonProperty("title")
    private String obiettivo;

    private String descrizione;

    @JsonProperty("idOriginColony")
    private Integer idColonia;

    @JsonProperty("missionType")
    private String missionType;

    @JsonProperty("plannedStartAt")
    private OffsetDateTime dataInizioPrevista;

    @JsonProperty("plannedEndAt")
    private OffsetDateTime dataFinePrevista;

    @JsonProperty("priority")
    private String priorita;

    @JsonProperty("safetyLevel")
    private String safetyLevel;

    @JsonProperty("status")
    private StatoMissione stato;

    private Long idResponsabile;

    @JsonProperty("createdAt")
    private OffsetDateTime dataCreazione;

    @JsonProperty("lastModified")
    private OffsetDateTime ultimaModifica;

    public static MissioneResponse from(Missione missione, TipologiaMissione tipologia) {
        MissioneResponse response = new MissioneResponse();
        response.setId(missione.getId());
        response.setCodice(missione.getCodice());
        response.setObiettivo(missione.getObiettivo());
        response.setDescrizione(missione.getDescrizione());
        response.setIdColonia(missione.getColonia().getId());
        String nomeTipo = tipologia != null ? tipologia.getTipo()
                : (missione.getTipologia() != null ? missione.getTipologia().getTipo() : null);
        response.setMissionType(MissionApiMapper.toFrontendType(nomeTipo));
        response.setDataInizioPrevista(missione.getDataInizioPrevista());
        response.setDataFinePrevista(missione.getDataFinePrevista());
        response.setPriorita(MissionApiMapper.toFrontend(missione.getPriorita()));
        LivelloSicurezza livello = missione.getLivelloSicurezza();
        response.setSafetyLevel(MissionApiMapper.toFrontend(livello));
        response.setStato(missione.getStato());
        response.setIdResponsabile(missione.getResponsabile().getId());
        response.setDataCreazione(missione.getDataCreazione());
        response.setUltimaModifica(missione.getUltimaModifica());
        return response;
    }
}

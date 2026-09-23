package com.aether.backend.dto;

import com.aether.backend.entity.Missione;
import com.aether.backend.entity.Priorita;
import com.aether.backend.entity.Rischio;
import com.aether.backend.entity.StatoMissione;
import com.aether.backend.entity.TipologiaMissione;
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
    private Long idColonia;
    
    @JsonProperty("missionType")
    private String missionType;
    
    @JsonProperty("plannedStartAt")
    private OffsetDateTime dataInizioPrevista;
    
    @JsonProperty("plannedEndAt")
    private OffsetDateTime dataFinePrevista;
    
    @JsonProperty("priority")
    private Priorita priorita;
    
    @JsonProperty("safetyLevel")
    private Rischio rischio;
    
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
        response.setIdColonia(missione.getIdColonia());
        response.setMissionType(tipologia != null ? tipologia.getTipo() : "UNKNOWN");
        response.setDataInizioPrevista(missione.getDataInizioPrevista());
        response.setDataFinePrevista(missione.getDataFinePrevista());
        response.setPriorita(missione.getPriorita());
        response.setRischio(missione.getRischio());
        response.setStato(missione.getStato());
        response.setIdResponsabile(missione.getIdResponsabile());
        response.setDataCreazione(missione.getDataCreazione());
        response.setUltimaModifica(missione.getUltimaModifica());
        return response;
    }
}

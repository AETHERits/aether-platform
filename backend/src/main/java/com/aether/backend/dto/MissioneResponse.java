package com.aether.backend.dto;

import com.aether.backend.entity.Missione;
import com.aether.backend.entity.Priorita;
import com.aether.backend.entity.Rischio;
import com.aether.backend.entity.StatoMissione;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class MissioneResponse {

    private Long id;
    private String codice;
    private String obiettivo;
    private String descrizione;
    private Long idColonia;
    private Long idTipologia;
    private OffsetDateTime dataInizioPrevista;
    private OffsetDateTime dataFinePrevista;
    private Priorita priorita;
    private Rischio rischio;
    private StatoMissione stato;
    private Long idResponsabile;
    private OffsetDateTime dataCreazione;
    private OffsetDateTime ultimaModifica;

    public static MissioneResponse from(Missione missione) {
        MissioneResponse response = new MissioneResponse();
        response.setId(missione.getId());
        response.setCodice(missione.getCodice());
        response.setObiettivo(missione.getObiettivo());
        response.setDescrizione(missione.getDescrizione());
        response.setIdColonia(missione.getIdColonia());
        response.setIdTipologia(missione.getIdTipologia());
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

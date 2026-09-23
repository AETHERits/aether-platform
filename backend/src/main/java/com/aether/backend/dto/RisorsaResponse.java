package com.aether.backend.dto;

import com.aether.backend.entity.Risorsa;

public class RisorsaResponse {

    private final Long idRisorsa;
    private final String codice;
    private final String nome;
    private final String unitaMisura;
    private final boolean attivo;

    public RisorsaResponse(Long idRisorsa, String codice, String nome,
                            String unitaMisura, boolean attivo) {
        this.idRisorsa = idRisorsa;
        this.codice = codice;
        this.nome = nome;
        this.unitaMisura = unitaMisura;
        this.attivo = attivo;
    }

    public static RisorsaResponse fromEntity(Risorsa risorsa) {
        return new RisorsaResponse(
                risorsa.getIdRisorsa(),
                risorsa.getCodice(),
                risorsa.getNome(),
                risorsa.getUnitaMisura(),
                risorsa.isAttivo()
        );
    }

    public Long getIdRisorsa() {
        return idRisorsa;
    }

    public String getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public String getUnitaMisura() {
        return unitaMisura;
    }

    public boolean isAttivo() {
        return attivo;
    }
}

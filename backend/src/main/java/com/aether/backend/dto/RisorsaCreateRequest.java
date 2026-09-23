package com.aether.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RisorsaCreateRequest {

    @NotBlank(message = "Il codice risorsa è obbligatorio")
    @Size(max = 40, message = "Il codice risorsa non può superare 40 caratteri")
    private String codice;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 120, message = "Il nome non può superare 120 caratteri")
    private String nome;

    @NotBlank(message = "L'unità di misura è obbligatoria")
    @Size(max = 20, message = "L'unità di misura non può superare 20 caratteri")
    private String unitaMisura;

    public RisorsaCreateRequest() {
    }

    public RisorsaCreateRequest(String codice, String nome, String unitaMisura) {
        this.codice = codice;
        this.nome = nome;
        this.unitaMisura = unitaMisura;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnitaMisura() {
        return unitaMisura;
    }

    public void setUnitaMisura(String unitaMisura) {
        this.unitaMisura = unitaMisura;
    }
}

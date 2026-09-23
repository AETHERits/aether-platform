package com.aether.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RisorsaUpdateRequest {

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 120, message = "Il nome non può superare 120 caratteri")
    private String nome;

    @NotBlank(message = "L'unità di misura è obbligatoria")
    @Size(max = 20, message = "L'unità di misura non può superare 20 caratteri")
    private String unitaMisura;

    public RisorsaUpdateRequest() {
    }

    public RisorsaUpdateRequest(String nome, String unitaMisura) {
        this.nome = nome;
        this.unitaMisura = unitaMisura;
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

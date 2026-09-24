package com.aether.backend.dto;

import com.aether.backend.entity.StatoMissione;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CambiaStatoRequest {
    @NotNull(message = "Il nuovo stato e' obbligatorio")
    private StatoMissione stato;
}

package com.example.missions.dto;

import com.example.missions.entity.Priority;
import com.example.missions.entity.RiskLevel;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Payload di creazione missione. Nota: NON contiene un campo "status":
 * lo stato iniziale è sempre DRAFT ed è deciso dal server, mai dal client.
 * La coerenza tra startDate/endDate viene verificata a livello di servizio
 * (non è esprimibile con una singola annotazione di campo).
 */
@Getter
@Setter
public class MissionCreateRequest {

    @NotBlank(message = "Il codice missione è obbligatorio")
    @Pattern(
            regexp = "^[A-Z0-9][A-Z0-9\\-_]{2,49}$",
            message = "Il codice missione deve contenere solo lettere maiuscole, numeri, '-' o '_' (3-50 caratteri)"
    )
    private String missionCode;

    @NotBlank(message = "L'obiettivo della missione è obbligatorio")
    @Size(max = 500, message = "L'obiettivo non può superare i 500 caratteri")
    private String objective;

    @NotNull(message = "L'id della base/colonia è obbligatorio")
    @Positive(message = "L'id della base/colonia deve essere un numero positivo")
    private Long baseId;

    @NotNull(message = "La data di inizio è obbligatoria")
    private LocalDateTime startDate;

    @NotNull(message = "La data di fine è obbligatoria")
    private LocalDateTime endDate;

    @NotNull(message = "La priorità è obbligatoria")
    private Priority priority;

    @NotNull(message = "Il livello di rischio è obbligatorio")
    private RiskLevel riskLevel;

    @NotNull(message = "Il punteggio di rischio è obbligatorio")
    @Min(value = 0, message = "Il punteggio di rischio non può essere inferiore a 0")
    @Max(value = 100, message = "Il punteggio di rischio non può superare 100")
    private Integer riskScore;

}

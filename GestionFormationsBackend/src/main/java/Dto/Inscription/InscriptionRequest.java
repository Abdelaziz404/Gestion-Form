package Dto.Inscription;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import Enum.StatutInscription;

@Getter
@Setter
public class InscriptionRequest {

    @NotNull(message = "Participant ID cannot be null")
    private Long participantId;

    @NotNull(message = "Formation ID cannot be null")
    private Long formationId;

    private StatutInscription status;
}
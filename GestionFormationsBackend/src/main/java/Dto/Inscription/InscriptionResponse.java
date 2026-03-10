package Dto.Inscription;

import lombok.Getter;
import lombok.Setter;
import Enum.StatutInscription;
import java.time.LocalDateTime;

@Getter
@Setter
public class InscriptionResponse {

    private Long id;

    private Long participantId;
    private String participantFullName; // pour affichage frontend

    private Long formationId;
    private String formationTitle; // pour affichage frontend

    private LocalDateTime dateInscription;
    private StatutInscription statutInscription;

    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}
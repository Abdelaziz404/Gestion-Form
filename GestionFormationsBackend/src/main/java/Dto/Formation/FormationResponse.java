package Dto.Formation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import Enum.StatusFormation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormationResponse {

    private Long id;
    private String title;
    private String description;
    private BigDecimal prix;
    private Integer nombreParticipantMin;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private LocalDateTime dateDebutCours;
    private StatusFormation status;
    private String imageUrl;

    // Formateur info for frontend
    private Long formateurId;
    private String formateurFullName;
}
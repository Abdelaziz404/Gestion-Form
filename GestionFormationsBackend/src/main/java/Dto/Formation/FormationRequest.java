package Dto.Formation;

import Enum.StatusFormation;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FormationRequest {

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotBlank
    private String description;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal prix;

    @NotBlank(message = "Full name of the formateur is required")
    private String formateurFullName; // frontend provides "prenom nom"

    @NotNull
    @Min(1)
    private Integer nombreParticipantMin;

    private LocalDateTime dateDebutCours;

    @NotNull
    private StatusFormation status;

    private String imageUrl; // optional, can use Cloudinary
}
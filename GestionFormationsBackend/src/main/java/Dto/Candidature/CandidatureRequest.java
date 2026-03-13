package Dto.Candidature;

import Enum.StatutInscription;
import Util.ValidationConstants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CandidatureRequest {

    // ---------------- Personal Info ----------------
    @NotBlank(message = ValidationConstants.NOT_BLANK_ERROR_MSG)
    @Pattern(regexp = ValidationConstants.NAME_REGEX, message = ValidationConstants.NAME_ERROR_MSG)
    private String nom;

    @NotBlank(message = ValidationConstants.NOT_BLANK_ERROR_MSG)
    @Pattern(regexp = ValidationConstants.NAME_REGEX, message = ValidationConstants.NAME_ERROR_MSG)
    private String prenom;

    @NotBlank(message = ValidationConstants.NOT_BLANK_ERROR_MSG)
    @Email(message = ValidationConstants.EMAIL_ERROR_MSG)
    private String email;

    // ---------------- Professional Info ----------------
    @NotNull(message = "Le nombre d'années d'expérience est obligatoire")
    @Min(value = 0, message = "Les années d'expérience doivent être positives")
    private Integer anneesExperience;

    @NotBlank(message = ValidationConstants.NOT_BLANK_ERROR_MSG)
    private String specialites;

    // ---------------- Files ----------------
    private List<String> files; // URLs of uploaded PDFs

    // ---------------- Status (optional) ----------------
    private StatutInscription status; // Optional, defaults to EN_ATTENTE if not provided
}
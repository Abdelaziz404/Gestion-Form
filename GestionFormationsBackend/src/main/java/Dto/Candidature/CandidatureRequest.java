package Dto.Candidature;

import Enum.StatutInscription;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CandidatureRequest {

    // ---------------- Personal Info ----------------
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;

    // ---------------- Professional Info ----------------
    @NotNull(message = "Le nombre d'années d'expérience est obligatoire")
    @Min(value = 0, message = "Les années d'expérience doivent être positives")
    private Integer anneesExperience;

    @NotBlank(message = "La spécialité est obligatoire")
    private String specialites;

    // ---------------- Files ----------------
    private List<String> files; // URLs of uploaded PDFs

    // ---------------- Status (optional) ----------------
    private StatutInscription status; // Optional, defaults to EN_ATTENTE if not provided
}
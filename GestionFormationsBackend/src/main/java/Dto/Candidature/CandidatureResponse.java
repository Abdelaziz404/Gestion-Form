package Dto.Candidature;

import Enum.StatutInscription;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidatureResponse {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Integer anneesExperience;
    private String specialites;
    private StatutInscription status;

    private List<String> files; // chargé seulement dans /files
}
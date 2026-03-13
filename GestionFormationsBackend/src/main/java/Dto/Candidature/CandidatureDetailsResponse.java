package Dto.Candidature;

import Entity.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import Enum.StatutInscription;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidatureDetailsResponse {

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private Integer anneesExperience;
    private String specialites;
    private List<Document> documents;
    private StatutInscription status;

}
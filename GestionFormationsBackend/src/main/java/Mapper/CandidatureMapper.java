package Mapper;

import Dto.Candidature.CandidatureRequest;
import Dto.Candidature.CandidatureResponse;
import Entity.Candidature;
import Enum.StatutInscription;

public final class CandidatureMapper {

    private CandidatureMapper() {}

    public static Candidature toEntity(CandidatureRequest request) {

        StatutInscription resolvedStatus;
        if (request.getStatus() != null) {
            resolvedStatus = request.getStatus();
        } else {
            resolvedStatus = StatutInscription.EN_ATTENTE;
        }

        return Candidature.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .anneesExperience(request.getAnneesExperience())
                .specialites(request.getSpecialites())
                .files(request.getFiles())
                .status(resolvedStatus)
                .motDePasse("") // Bypasses lingering DB NOT NULL constraints
                .build();
    }

    public static CandidatureResponse toResponse(Candidature candidature) {

        return CandidatureResponse.builder()
                .id(candidature.getId())
                .nom(candidature.getNom())
                .prenom(candidature.getPrenom())
                .email(candidature.getEmail())
                .anneesExperience(candidature.getAnneesExperience())
                .specialites(candidature.getSpecialites())
                .status(candidature.getStatus())
                .files(candidature.getFiles())
                .build();
    }

}
package Mapper;

import Dto.Candidature.CandidatureRequest;
import Enum.StatutInscription;
import Entity.Candidature;

public final class CandidatureMapper {

    private CandidatureMapper() {}

    // -------------------- CREATE ENTITY --------------------
    public static Candidature toEntity(CandidatureRequest request, String encodedPassword) {
        return Candidature.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .motDePasse(encodedPassword)
                .anneesExperience(request.getAnneesExperience())
                .specialites(request.getSpecialites())
                .files(request.getFiles())
                .status(request.getStatus() != null ? request.getStatus() : StatutInscription.EN_ATTENTE)
                .build();
    }

    // -------------------- UPDATE EXISTING ENTITY --------------------
    /*
    public static void updateEntity(Candidature existing, CandidatureRequest request, String encodedPassword) {
        if (request.getNom() != null) existing.setNom(request.getNom());
        if (request.getPrenom() != null) existing.setPrenom(request.getPrenom());
        if (request.getEmail() != null) existing.setEmail(request.getEmail());
        if (encodedPassword != null && !encodedPassword.isEmpty()) existing.setMotDePasse(encodedPassword);
        if (request.getAnneesExperience() != null) existing.setAnneesExperience(request.getAnneesExperience());
        if (request.getSpecialites() != null) existing.setSpecialites(request.getSpecialites());
        if (request.getFiles() != null) existing.setFiles(request.getFiles());
        if (request.getStatus() != null) existing.setStatus(request.getStatus());
    } */
}
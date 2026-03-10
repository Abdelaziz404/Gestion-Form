package Mapper;

import Dto.Formation.FormationRequest;
import Dto.Formation.FormationResponse;
import Entity.Formation;

public final class FormationMapper {

    private FormationMapper() {}

    // ---------------- Entity <- Request ----------------
    public static Formation toEntity(FormationRequest request, Long formateurId, String formateurFullName) {
        Formation formation = Formation.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .prix(request.getPrix())
                .nombreParticipantMin(request.getNombreParticipantMin())
                .dateDebutCours(request.getDateDebutCours())
                .status(request.getStatus())
                .imageUrl(request.getImageUrl())
                .build();

        // Si besoin de lier un formateur existant, tu peux le faire ici
        // formation.setFormateur(formateurService.findById(formateurId));
        // ou laisser le service qui gère la création le faire

        return formation;
    }

    // ---------------- Entity -> Response ----------------
    public static FormationResponse toResponse(Formation formation) {
        FormationResponse response = new FormationResponse();
        response.setId(formation.getId());
        response.setTitle(formation.getTitle());
        response.setDescription(formation.getDescription());
        response.setPrix(formation.getPrix());
        response.setDateDebutCours(formation.getDateDebutCours());
        response.setStatus(formation.getStatus());
        response.setImageUrl(formation.getImageUrl());
        response.setNombreParticipantMin(formation.getNombreParticipantMin());

        // Mapper le formateur en affichant son nom complet si nécessaire
        if (formation.getFormateur() != null) {
            response.setFormateurFullName(
                    formation.getFormateur().getPrenom() + " " + formation.getFormateur().getNom()
            );
            response.setFormateurId(formation.getFormateur().getId());
        }

        return response;
    }

    // ---------------- Update existing entity ----------------
    public static void updateEntity(Formation existing, FormationRequest request) {
        if (request.getTitle() != null) existing.setTitle(request.getTitle());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getPrix() != null) existing.setPrix(request.getPrix());
        if (request.getNombreParticipantMin() != null) existing.setNombreParticipantMin(request.getNombreParticipantMin());
        if (request.getDateDebutCours() != null) existing.setDateDebutCours(request.getDateDebutCours());
        if (request.getStatus() != null) existing.setStatus(request.getStatus());
        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) existing.setImageUrl(request.getImageUrl());
        // Remarque : le formateur ne doit pas être changé ici, gérer via un service séparé si nécessaire
    }
}
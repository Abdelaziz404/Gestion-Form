package Mapper;

import Dto.Person.Participant.ParticipantRequest;
import Dto.Person.Participant.ParticipantResponse;
import Entity.Participant;

public final class ParticipantMapper {

    private ParticipantMapper() {
    }

    // -------------------- CREATE ENTITY --------------------
    public static Participant toEntity(ParticipantRequest request, Long personId) {
        return Participant.builder()
                .id(personId)
                .dateInscription(request.getDateInscription())
                .build();
    }

    // -------------------- TO DTO RESPONSE --------------------
    public static ParticipantResponse toResponse(Participant participant) {
        ParticipantResponse response = new ParticipantResponse();
        response.setDateInscription(participant.getDateInscription());
        response.setId(participant.getId());
        response.setPrenom(participant.getPrenom());
        response.setNom(participant.getNom());
        response.setEmail(participant.getEmail());
        response.setTelephone(participant.getTelephone());
        response.setImgSrc(participant.getImageUrl());
        response.setRole(participant.getRole());
        return response;
    }

    // -------------------- UPDATE EXISTING ENTITY --------------------
    public static void updateEntity(Participant existing, ParticipantRequest request) {
        if (request.getDateInscription() != null) {
            existing.setDateInscription(request.getDateInscription());
        }
        if (request.getPrenom() != null) {
            existing.setPrenom(request.getPrenom());
        }
        if (request.getNom() != null) {
            existing.setNom(request.getNom());
        }
        if (request.getEmail() != null) {
            existing.setEmail(request.getEmail());
        }
        if (request.getTelephone() != null) {
            existing.setTelephone(request.getTelephone());
        }
        if (request.getImageUrl() != null) {
            existing.setImageUrl(request.getImageUrl());
        }
        // Pour le rôle, on peut décider de le laisser inchangé
        // existing.setRole(request.getRole());
    }
}
package Mapper;

import Dto.Inscription.InscriptionRequest;
import Dto.Inscription.InscriptionResponse;
import Entity.Inscription;
import Entity.Participant;
import Entity.Formation;
import Enum.StatutInscription;

import java.time.LocalDateTime;

public final class InscriptionMapper {

    private InscriptionMapper() {}

    // ---------------- Request -> Entity ----------------
    public static Inscription toEntity(InscriptionRequest request, Participant participant, Formation formation) {
        Inscription inscription = new Inscription();
        inscription.setParticipant(participant);
        inscription.setFormation(formation);
        inscription.setDateInscription(LocalDateTime.now());

        // Status par défaut EN_ATTENTE si null
        inscription.setStatutInscription(
                request.getStatus() != null ? request.getStatus().name() : StatutInscription.EN_ATTENTE.name()
        );

        return inscription;
    }

    // ---------------- Entity -> Response ----------------
    public static InscriptionResponse toResponse(Inscription inscription) {
        InscriptionResponse response = new InscriptionResponse();
        response.setId(inscription.getId());
        response.setParticipantId(inscription.getParticipant().getId());
        response.setParticipantFullName(inscription.getParticipant().getFullName());
        response.setFormationId(inscription.getFormation().getId());
        response.setFormationTitle(inscription.getFormation().getTitle());
        response.setStatutInscription(
                inscription.getStatutInscription() != null
                        ? StatutInscription.valueOf(inscription.getStatutInscription())
                        : StatutInscription.EN_ATTENTE
        );
        response.setDateInscription(inscription.getDateInscription());
        response.setDateCreation(inscription.getDateCreation());
        response.setDateModification(inscription.getDateModification());
        return response;
    }
}
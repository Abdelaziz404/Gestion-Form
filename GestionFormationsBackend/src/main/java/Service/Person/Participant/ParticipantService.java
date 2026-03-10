package Service.Person.Participant;

import Dto.Person.Participant.ParticipantRequest;
import Dto.Person.Participant.ParticipantResponse;

import java.time.LocalDate;
import java.util.List;

public interface ParticipantService {

    // ================= CREATE =================
    ParticipantResponse createParticipantWithPersonId(ParticipantRequest request, Long personId);

    // ================= UPDATE =================
    ParticipantResponse updateParticipantWithPersonId(Long personId, ParticipantRequest request);

    ParticipantResponse updateDateInscription(Long participantId, LocalDate dateInscription);

    // ================= GET =================
    ParticipantResponse getById(Long id);

    ParticipantResponse getByEmail(String email);

    ParticipantResponse getByPhone(String phone);

    List<ParticipantResponse> findAllResponse();
}
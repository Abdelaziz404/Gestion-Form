package Service.Person.Participant;

import Dto.Person.Participant.ParticipantRequest;
import Dto.Person.Participant.ParticipantResponse;
import Entity.Participant;
import Exception.EntityNotFoundException;
import Exception.ValidationException;
import Mapper.ParticipantMapper;
import Repository.ParticipantRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    public ParticipantServiceImpl(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    // ---------------- CREATE ----------------
    @Override
    @CacheEvict(value = { "participants", "participantResponses" }, allEntries = true)
    public ParticipantResponse createParticipantWithPersonId(ParticipantRequest request, Long personId) {
        if (request == null)
            throw new ValidationException("ParticipantRequest cannot be null");
        if (personId == null)
            throw new ValidationException("Person ID cannot be null");

        Participant participant = ParticipantMapper.toEntity(request, personId);
        participantRepository.save(participant);

        return ParticipantMapper.toResponse(participant);
    }

    // ---------------- UPDATE ----------------
    @Override
    @CacheEvict(value = { "participants", "participantResponses" }, allEntries = true)
    public ParticipantResponse updateParticipantWithPersonId(Long personId, ParticipantRequest request) {
        if (request == null)
            throw new ValidationException("ParticipantRequest cannot be null");
        if (personId == null)
            throw new ValidationException("Person ID cannot be null");

        Participant existing = participantRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Participant not found with id: " + personId));

        ParticipantMapper.updateEntity(existing, request);
        participantRepository.save(existing);

        return ParticipantMapper.toResponse(existing);
    }
    @Override
    @CacheEvict(value = { "participants", "participantResponses" }, allEntries = true)
    public ParticipantResponse updateDateInscription(Long participantId, LocalDate dateInscription) {
        Participant existing = participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("Participant not found with id: " + participantId));

        existing.setDateInscription(Optional.ofNullable(dateInscription).orElse(LocalDate.now()));
        participantRepository.save(existing);

        return ParticipantMapper.toResponse(existing);
    }


    private Participant getEntityById(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Participant not found with id: " + id));
    }

    // ---------------- GET (DTO) ----------------
    @Override
    @Cacheable(value = "participantResponses", key = "'id_' + #id")
    public ParticipantResponse getById(Long id) {
        Participant participant = getEntityById(id);
        return ParticipantMapper.toResponse(participant);
    }

    @Override
    @Cacheable(value = "participantResponses", key = "'email_' + #email")
    public ParticipantResponse getByEmail(String email) {
        Participant participant = participantRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Participant not found with email: " + email));
        return ParticipantMapper.toResponse(participant);
    }

    @Override
    @Cacheable(value = "participantResponses", key = "'phone_' + #phone")
    public ParticipantResponse getByPhone(String phone) {
        Participant participant = participantRepository.findByTelephone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Participant not found with phone: " + phone));
        return ParticipantMapper.toResponse(participant);
    }

    @Override
    public List<ParticipantResponse> findAllResponse() {
        List<ParticipantResponse> list = participantRepository.findAll().stream()
                .map(ParticipantMapper::toResponse)
                .collect(Collectors.toList());
        java.util.Collections.reverse(list);
        return list;
    }
}
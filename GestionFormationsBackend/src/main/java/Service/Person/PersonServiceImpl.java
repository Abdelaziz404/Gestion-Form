package Service.Person;

import Dto.Person.Admin.AdminRequest;
import Dto.Person.Formateur.FormateurRequest;
import Dto.Person.Participant.ParticipantRequest;
import Dto.Person.PersonRequest;
import Dto.Person.PersonResponse;
import Entity.Person;
import Exception.EntityNotFoundException;
import Repository.PersonRepository;
import Service.Person.Admin.AdminService;
import Service.Person.Formateur.FormateurService;
import Service.Person.Participant.ParticipantService;
import Service.Validators.EmailExistsValidator;
import Mapper.PersonMapper;
import Util.PasswordUtil;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static Util.Constants.DEFAULT_IMAGE;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final AdminService adminService;
    private final FormateurService formateurService;
    private final ParticipantService participantService;
    private final PasswordUtil passwordUtil;

    public PersonServiceImpl(
            PersonRepository personRepository,
            AdminService adminService,
            FormateurService formateurService,
            ParticipantService participantService,
            PasswordUtil passwordUtil) {
        this.personRepository = personRepository;
        this.adminService = adminService;
        this.formateurService = formateurService;
        this.participantService = participantService;
        this.passwordUtil = passwordUtil;
    }

    // ================= CREATE =================
    @Override
    public PersonResponse createPerson(PersonRequest request) {

        if (request == null)
            throw new ValidationException("PersonRequest cannot be null");

        EmailExistsValidator.validate(personRepository, request.getEmail());

        String encodedPassword = passwordUtil.encodePassword(request.getPassword());

        // Si aucune image fournie, utiliser valeur par défaut
        if (request.getImageUrl() == null || request.getImageUrl().isBlank()) {
            request.setImageUrl(DEFAULT_IMAGE);
        }

        request.setPassword(encodedPassword);

        return switch (request.getRole()) {
            case ADMIN -> adminService.createAdminWithPersonId((AdminRequest) request, null);
            case FORMATEUR -> formateurService.createFormateurWithPersonId((FormateurRequest) request, null);
            case PARTICIPANT -> participantService.createParticipantWithPersonId((ParticipantRequest) request, null);
            default -> {
                Person person = PersonMapper.toEntity(request, encodedPassword);
                personRepository.save(person);
                yield PersonMapper.toResponse(person);
            }
        };
    }

    // ================= UPDATE =================
    @Override
    public PersonResponse updatePerson(Long id, PersonRequest request) {

        if (request == null) {
            throw new ValidationException("PersonRequest cannot be null");
        }

        if (id == null) {
            throw new ValidationException("Person ID cannot be null");
        }

        Person existing = getEntityById(id);

        if (!request.getEmail().equals(existing.getEmail())) {
            EmailExistsValidator.validate(personRepository, request.getEmail());
        }

        String encodedPassword = null;
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            encodedPassword = passwordUtil.encodePassword(request.getPassword());
        }

        PersonMapper.updateEntityFromRequest(existing, request, encodedPassword);

        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            existing.setImageUrl(request.getImageUrl());
        }

        personRepository.save(existing);

        handleRoleUpdate(existing, request);

        return PersonMapper.toResponse(existing);
    }

    // ================= DELETE =================
    @Override
    public void deletePerson(Long id) {
        Person existing = getEntityById(id);
        personRepository.deleteById(existing.getId());
    }

    // ================= GET =================
    @Override
    public PersonResponse getById(Long id) {
        return PersonMapper.toResponse(getEntityById(id));
    }

    @Override
    public PersonResponse getByEmail(String email) {
        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        return PersonMapper.toResponse(person);
    }

    @Override
    public PersonResponse getByPhone(String phone) {
        Person person = personRepository.findByTelephone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        return PersonMapper.toResponse(person);
    }

    // ================= ROLE HANDLERS =================


    private void handleRoleUpdate(Person person, PersonRequest request) {
        switch (person.getRole()) {
            case ADMIN -> adminService.updateAdminWithPersonId(person.getId(), (AdminRequest) request);
            case FORMATEUR ->
                    formateurService.updateFormateurWithPersonId(person.getId(), (FormateurRequest) request);
            case PARTICIPANT ->
                    participantService.updateParticipantWithPersonId(person.getId(), (ParticipantRequest) request);
        }
    }

    @Override
    public PersonResponse findById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        return PersonMapper.toResponse(person);
    }

    public List<PersonResponse> findAllResponse() {
        return personRepository.findAll()
                .stream()
                .map(PersonMapper::toResponse)
                .toList();
    }

    // ================= UTILS =================
    private Person getEntityById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));
    }
}
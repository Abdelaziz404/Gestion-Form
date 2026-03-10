package Service.Candidature;

import Dto.Candidature.CandidatureRequest;
import Dto.Person.Formateur.FormateurRequest;
import Mapper.CandidatureMapper;
import Repository.CandidatureRepository;
import Repository.PersonRepository;
import Service.Person.PersonService;
import Service.Validators.EmailExistsValidator;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import Entity.Candidature;
import Enum.StatutInscription;
import Enum.Role;

import java.util.Optional;

@Service
public class CandidatureServiceImpl implements CandidatureService{
    private final PersonRepository personRepository;
    private final CandidatureRepository candidatureRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonService personService;

    public CandidatureServiceImpl(
            CandidatureRepository candidatureRepository,
            PersonRepository personRepository,
            PersonService personService,
            PasswordEncoder passwordEncoder
    ){
        this.candidatureRepository = candidatureRepository;
        this.personRepository = personRepository;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createCandidature(CandidatureRequest request){
        if(request == null){
            throw new ValidationException("CandidatureRequest cannot be null");
        }

        EmailExistsValidator.validate(personRepository, request.getEmail());

        String encodedPassword = passwordEncoder.encode(request.getMotDePasse());
        candidatureRepository.save(CandidatureMapper.toEntity(request, encodedPassword));
    }

    @Override
    public void accepterCandidature(Long id) {

        if (id == null) {
            throw new ValidationException("ID cannot be null");
        }

        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Candidature not found with ID: " + id));

        // Build FormateurRequest from candidature
        FormateurRequest request = getFormateurRequest(candidature);

        // Create person + formateur
        personService.createPerson(request);

        // Update candidature status
        candidature.setStatus(StatutInscription.ACCEPTER);

        candidatureRepository.save(candidature);
    }

    private static FormateurRequest getFormateurRequest(Candidature candidature) {
        FormateurRequest request = new FormateurRequest();

        request.setPrenom(candidature.getPrenom());
        request.setNom(candidature.getNom());
        request.setEmail(candidature.getEmail());
        request.setPassword(candidature.getMotDePasse()); // already encoded
        request.setRole(Role.FORMATEUR);

        request.setSpecialite(candidature.getSpecialites());
        request.setAnneesExperience(candidature.getAnneesExperience());

        request.setSalaire(0.0); // default value

        request.setDocumentUrls(candidature.getFiles());
        return request;
    }

    @Override
    public void changeCandidatureStatus(Long id, StatutInscription status){
        if(id == null){
            throw new ValidationException("ID cannot be null");
        }

        Optional<Candidature> candidatureOpt = candidatureRepository.findById(id);
        if (candidatureOpt.isPresent()) {
            Candidature candidature = candidatureOpt.get();
            candidature.setStatus(status);
            candidatureRepository.save(candidature);
        } else {
            throw new ValidationException("Candidature not found with ID: " + id);
        }
    }
}

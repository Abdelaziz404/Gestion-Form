package Service.Candidature;

import Dto.Candidature.CandidatureRequest;
import Dto.Candidature.CandidatureResponse;
import Dto.Person.Formateur.FormateurRequest;
import Entity.Candidature;
import Enum.Role;
import Enum.StatutInscription;
import Exception.BusinessRuleException;
import Mapper.CandidatureMapper;
import Repository.CandidatureRepository;
import Repository.PersonRepository;
import Service.Cloud.CloudinaryService;
import Service.Email.EmailService;
import Service.Person.PersonService;
import Service.Validators.EmailExistsValidator;
import Service.Validators.EntityExistsByIdValidator;
import Util.PasswordGenerator;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CandidatureServiceImpl implements CandidatureService {

    private final PersonRepository personRepository;
    private final CandidatureRepository candidatureRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonService personService;
    private final EmailService emailService;
    private final CloudinaryService cloudinaryService;

    public CandidatureServiceImpl(
            CandidatureRepository candidatureRepository,
            PersonRepository personRepository,
            PersonService personService,
            PasswordEncoder passwordEncoder,
            EmailService emailService,
            CloudinaryService cloudinaryService
    ) {
        this.candidatureRepository = candidatureRepository;
        this.personRepository = personRepository;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.cloudinaryService = cloudinaryService;
    }

    // ---------------- CREATE CANDIDATURE ----------------
    @Override
    public void createCandidature(CandidatureRequest request, List<MultipartFile> files) {
        if (request == null) throw new ValidationException("CandidatureRequest cannot be null");

        // Vérifie si l'email existe déjà
        EmailExistsValidator.validate(personRepository, request.getEmail());

        // Upload fichiers vers Cloudinary
        try {
            if (files != null && !files.isEmpty()) {
                request.setFiles(cloudinaryService.uploadMultipleFiles(files));
            }
        } catch (IOException e) {
            throw new BusinessRuleException("Erreur upload fichiers: " + e.getMessage());
        }

        // Sauvegarde la candidature
        candidatureRepository.save(CandidatureMapper.toEntity(request));
    }

    // ---------------- ACCEPT CANDIDATURE ----------------
    @Override
    public void accepterCandidature(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Candidature introuvable"));

        // Génération mot de passe
        String generatedPassword = PasswordGenerator.generatePassword(8);
        String encodedPassword = passwordEncoder.encode(generatedPassword);

        // Création Formateur à partir de la candidature
        FormateurRequest formateurRequest = new FormateurRequest();
        formateurRequest.setNom(candidature.getNom());
        formateurRequest.setPrenom(candidature.getPrenom());
        //EmailExistsValidator.validate(personRepository, candidature.getEmail());
        formateurRequest.setEmail(candidature.getEmail());
        formateurRequest.setPassword(generatedPassword);
        formateurRequest.setRole(Role.FORMATEUR);
        formateurRequest.setSpecialite(candidature.getSpecialites());
        formateurRequest.setAnneesExperience(candidature.getAnneesExperience());
        formateurRequest.setDocumentUrls(candidature.getFiles());

        personService.createPerson(formateurRequest);

        // Met à jour le statut
        candidature.setStatus(StatutInscription.ACCEPTER);
        candidatureRepository.save(candidature);

        // Envoi email
        String message = String.format(
                "Bonjour %s %s,\n\nVotre candidature a été acceptée.\nEmail: %s\nMot de passe: %s\n\nBienvenue !",
                candidature.getPrenom(), candidature.getNom(),
                candidature.getEmail(), generatedPassword
        );
        emailService.sendEmail(candidature.getEmail(), "Candidature acceptée", message);
    }

    // ---------------- CHANGE STATUS ----------------
    @Override
    public void changeCandidatureStatus(Long id, StatutInscription status) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Candidature introuvable"));
        candidature.setStatus(status);
        candidatureRepository.save(candidature);
    }

    // ---------------- DELETE CANDIDATURE ----------------
    @Override
    public void deleteCandidature(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Candidature introuvable"));
        candidatureRepository.delete(candidature);
    }

    // ---------------- CHECK EMAIL ----------------
    @Override
    public boolean checkEmail(String email) {
        if (email == null) throw new ValidationException("Email ne peut pas être null");

        boolean existsInCandidature = candidatureRepository.findAll().stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(email) && c.getStatus() != StatutInscription.REFUSER);

        boolean existsInPerson = personRepository.findByEmailIgnoreCase(email).isPresent();

        return existsInCandidature || existsInPerson;
    }

    // ---------------- GET ALL CANDIDATURES ----------------
    @Override
    public List<CandidatureResponse> getAllCandidatures() {
        return candidatureRepository.findAll().stream()
                .map(CandidatureMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ---------------- GET FILES ----------------
    @Override
    public List<String> getCandidatureFiles(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Candidature introuvable"));
        return candidature.getFiles();
    }
}
package Service.Person.Formateur;

import Dto.Person.Formateur.FormateurRequest;
import Dto.Person.Formateur.FormateurResponse;
import Entity.Document;
import Entity.Formateur;
import Exception.EntityNotFoundException;
import Exception.ValidationException;
import Mapper.FormateurMapper;
import Repository.FormateurRepository;
import Service.Configuration.ConfigurationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FormateurServiceImpl implements FormateurService {

    private final FormateurRepository formateurRepository;
    private final ConfigurationService configurationService;

    public FormateurServiceImpl(FormateurRepository formateurRepository,
                                ConfigurationService configurationService) {
        this.formateurRepository = formateurRepository;
        this.configurationService = configurationService;
    }

    // ---------------- CREATE ----------------
    @Override
    public FormateurResponse createFormateurWithPersonId(FormateurRequest request, Long personId) {

        if (request == null)
            throw new ValidationException("FormateurRequest cannot be null");

        // --- GET DEFAULT SALARY FROM CONFIGURATION ---
        if (request.getSalaire() == null) {
            Double defaultSalary = configurationService.getValue("default_salary")
                    .map(Double::parseDouble)
                    .orElse(3000.0); // fallback default
            request.setSalaire(defaultSalary);
        }

        // Mapper crée l'entité Formateur + liste de Documents si documentUrls présentes
        Formateur formateur = FormateurMapper.toEntity(request, personId);

        formateurRepository.save(formateur);

        return FormateurMapper.toResponse(formateur);
    }

    // ---------------- UPDATE ENTIER ----------------
    @Override
    @CacheEvict(value = "formateurs", allEntries = true)
    public FormateurResponse updateFormateurWithPersonId(Long personId, FormateurRequest request) {

        if (request == null)
            throw new ValidationException("FormateurRequest cannot be null");
        if (personId == null)
            throw new ValidationException("Person ID cannot be null");

        Formateur existing = getEntityById(personId);

        // Update des champs standards et documents
        FormateurMapper.updateEntity(existing, request);

        if (request.getDocumentUrls() != null && !request.getDocumentUrls().isEmpty()) {

            Set<Document> newDocuments = new HashSet<>();
            for (String url : request.getDocumentUrls()) {
                Document doc = Document.builder()
                        .documentUrl(url)
                        .formateur(existing)
                        .build();
                newDocuments.add(doc);
            }

            existing.getDocuments().addAll(newDocuments);
        }

        formateurRepository.save(existing);

        return FormateurMapper.toResponse(existing);
    }

    // ---------------- UPDATE FIELDS ----------------
    @Override
    @CacheEvict(value = "formateurs", allEntries = true)
    public FormateurResponse updateSpecialite(Long formateurId, String specialite) {
        Formateur existing = getEntityById(formateurId);
        if (specialite != null)
            existing.setSpecialite(specialite);
        formateurRepository.save(existing);
        return FormateurMapper.toResponse(existing);
    }

    @Override
    @CacheEvict(value = "formateurs", allEntries = true)
    public FormateurResponse updateExperience(Long formateurId, Integer anneesExperience) {
        Formateur existing = getEntityById(formateurId);
        if (anneesExperience != null)
            existing.setAnneesExperience(anneesExperience);
        formateurRepository.save(existing);
        return FormateurMapper.toResponse(existing);
    }

    @Override
    @CacheEvict(value = "formateurs", allEntries = true)
    public FormateurResponse updateSalaire(Long formateurId, Double salaire) {
        Formateur existing = getEntityById(formateurId);
        if (salaire != null)
            existing.setSalaire(salaire);
        formateurRepository.save(existing);
        return FormateurMapper.toResponse(existing);
    }

    // ---------------- GET ----------------
    @Override
    public FormateurResponse getById(Long id) {
        Formateur formateur = getEntityById(id);
        return FormateurMapper.toResponse(formateur);
    }

    @Override
    public List<FormateurResponse> findAllFormateur() {
        return formateurRepository.findAll()
                .stream()
                .map(FormateurMapper::toResponse)
                .toList();
    }

    // ---------------- UTILS ----------------
    private Formateur getEntityById(Long id) {
        return formateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formateur not found with id: " + id));
    }
}
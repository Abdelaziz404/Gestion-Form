package Service.Formation;

import Dto.Formation.FormationRequest;
import Dto.Formation.FormationResponse;
import Entity.Formateur;
import Entity.Formation;
import Mapper.FormationMapper;
import Repository.FormateurRepository;
import Repository.FormationRepository;
import Repository.InscriptionRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import Exception.EntityNotFoundException;
import Enum.StatusFormation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormationServiceImpl implements FormationService {

    private final FormationRepository formationRepository;
    private final FormateurRepository formateurRepository;
    private final InscriptionRepository inscriptionRepository;

    public FormationServiceImpl(
            FormationRepository formationRepository,
            FormateurRepository formateurRepository,
            InscriptionRepository inscriptionRepository
    ) {
        this.formationRepository = formationRepository;
        this.formateurRepository = formateurRepository;
        this.inscriptionRepository = inscriptionRepository;
    }

    // ================= CREATE =================
    @Override
    public FormationResponse createFormation(FormationRequest request, Long formateurId) {

        if (request == null) {
            throw new ValidationException("FormationRequest cannot be null");
        }

        Formateur formateur = formateurRepository.findById(formateurId)
                .orElseThrow(() -> new EntityNotFoundException("Formateur not found with id: " + formateurId));

        Formation formation = FormationMapper.toEntity(request, formateurId, request.getFormateurFullName());
        formation.setFormateur(formateur);

        formationRepository.save(formation);
        updateStatusForFormation(formation); // Check if status should be auto-updated

        return FormationMapper.toResponse(formation);
    }

    // ================= UPDATE =================
    @Override
    public FormationResponse updateFormation(Long id, FormationRequest request) {

        if (request == null) {
            throw new ValidationException("FormationRequest cannot be null");
        }

        Formation existing = formationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with id: " + id));

        if (request.getFormateurFullName() != null) {
            Formateur formateur = formateurRepository.findById(existing.getFormateur().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Formateur not found with id: " + existing.getFormateur().getId()));
            existing.setFormateur(formateur);
        }

        FormationMapper.updateEntity(existing, request);
        formationRepository.save(existing);
        updateStatusForFormation(existing); // Check if status should be auto-updated

        return FormationMapper.toResponse(existing);
    }

    // ================= DELETE =================
    @Override
    public void deleteFormation(Long id) {
        Formation existing = formationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with id: " + id));
        formationRepository.delete(existing);
    }

    // ================= GET =================
    @Override
    public FormationResponse getById(Long id) {
        Formation existing = formationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with id: " + id));
        return FormationMapper.toResponse(existing);
    }

    @Override
    public List<FormationResponse> getAllFormations() {
        return formationRepository.findAll().stream()
                .peek(this::updateStatusForFormation) // Update status for each formation
                .map(FormationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FormationResponse updateFormationStatus(Long id, FormationRequest request, StatusFormation status){
        Formation existing = formationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with id: " + id));
        existing.setStatus(request.getStatus());
        return FormationMapper.toResponse(existing);
    }

    // ================= STATUS CHECK =================
    private void updateStatusForFormation(Formation formation) {
        if (formation.getDateDebutCours() == null) return;

        LocalDateTime now = LocalDateTime.now();
        if (!formation.getDateDebutCours().isAfter(now)) { // If dateDebutCours <= now
            long participantsCount = inscriptionRepository.countByFormationId(formation.getId());

            if (participantsCount >= formation.getNombreParticipantMin()) {
                formation.setStatus(StatusFormation.DEJA_LANCE);
            } else {
                formation.setStatus(StatusFormation.ANNULE);
            }
            formationRepository.save(formation);
        }
    }
}
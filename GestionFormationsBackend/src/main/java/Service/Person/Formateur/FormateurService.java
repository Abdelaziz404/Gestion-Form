package Service.Person.Formateur;

import Dto.Person.Formateur.FormateurRequest;
import Dto.Person.Formateur.FormateurResponse;

import java.util.List;

public interface FormateurService{

    // ---------------- CREATE METHOD ----------------
    FormateurResponse createFormateurWithPersonId(FormateurRequest request, Long personId);

    // ---------------- UPDATE METHOD (by ID + DTO) ----------------
    FormateurResponse updateFormateurWithPersonId(Long personId, FormateurRequest request);

    FormateurResponse updateSpecialite(Long formateurId, String specialite);

    FormateurResponse updateExperience(Long formateurId, Integer anneesExperience);

    FormateurResponse updateSalaire(Long formateurId, Double salaire);

    // ---------------- GET ----------------
    FormateurResponse getById(Long id);

    List<FormateurResponse> findAllFormateur();
}
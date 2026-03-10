package Service.Formation;

import Dto.Formation.FormationRequest;
import Dto.Formation.FormationResponse;
import Enum.StatusFormation;

import java.util.List;

public interface FormationService {

    FormationResponse createFormation(FormationRequest request, Long formateurId);

    FormationResponse updateFormation(Long id, FormationRequest request);

    void deleteFormation(Long id);

    FormationResponse getById(Long id);

    List<FormationResponse> getAllFormations();

    FormationResponse updateFormationStatus(Long id, FormationRequest request, StatusFormation status);
}

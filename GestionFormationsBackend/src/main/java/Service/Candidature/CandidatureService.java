package Service.Candidature;

import Dto.Candidature.CandidatureRequest;
import Enum.StatutInscription;

public interface CandidatureService {
    void createCandidature(CandidatureRequest request);
    void changeCandidatureStatus(Long id, StatutInscription status);
    void accepterCandidature(Long id);
}

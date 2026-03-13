package Service.Candidature;

import Dto.Candidature.CandidatureDetailsResponse;
import Dto.Candidature.CandidatureRequest;
import Dto.Candidature.CandidatureResponse;
import Enum.StatutInscription;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidatureService {
    void createCandidature(CandidatureRequest request, List<MultipartFile> files);
    void changeCandidatureStatus(Long id, StatutInscription status);
    void accepterCandidature(Long id);
    void deleteCandidature(Long id);
    boolean checkEmail(String email);
    List<CandidatureResponse> getAllCandidatures();
    List<String> getCandidatureFiles(Long id);
}

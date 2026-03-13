package Controller;

import Dto.Candidature.CandidatureRequest;
import Dto.Candidature.CandidatureResponse;
import Dto.Candidature.CandidatureDetailsResponse;
import Enum.StatutInscription;
import Service.Candidature.CandidatureService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/candidatures")
public class CandidatureController {

    private final CandidatureService candidatureService;

    public CandidatureController(CandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }

    // ---------------- CREATE ----------------
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> createCandidature(
            @Valid @RequestPart("candidature") CandidatureRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        // Vérifie si l'email existe déjà
        boolean emailPris = candidatureService.checkEmail(request.getEmail());
        if (emailPris) {
            return ResponseEntity
                    .badRequest()
                    .body("Cet email est déjà utilisé. Veuillez en choisir un autre.");
        }

        // Crée la candidature avec les fichiers
        candidatureService.createCandidature(request, files);

        return ResponseEntity
                .status(201)
                .body("Candidature créée avec succès");
    }

    // ---------------- ACCEPT ----------------
    @PutMapping("/{id}/accepter")
    public ResponseEntity<String> accepterCandidature(@PathVariable Long id) {
        candidatureService.accepterCandidature(id);
        return ResponseEntity.ok("Candidature acceptée et formateur créé");
    }

    // ---------------- CHANGE STATUS ----------------
    @PutMapping("/{id}/status")
    public ResponseEntity<String> changeStatus(
            @PathVariable Long id,
            @RequestParam StatutInscription status
    ) {
        candidatureService.changeCandidatureStatus(id, status);
        return ResponseEntity.ok("Statut de la candidature mis à jour");
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCandidature(@PathVariable Long id) {
        candidatureService.deleteCandidature(id);
        return ResponseEntity.ok("Candidature supprimée avec succès");
    }

    // ---------------- CHECK EMAIL ----------------
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        boolean exists = candidatureService.checkEmail(email);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    // ---------------- GET ALL CANDIDATURES ----------------
    @GetMapping
    public ResponseEntity<List<CandidatureResponse>> getAllCandidatures() {
        List<CandidatureResponse> list = candidatureService.getAllCandidatures();
        return ResponseEntity.ok(list);
    }


    @GetMapping("/{id}/files")
    public List<String> getCandidatureFiles(@PathVariable Long id) {
        return candidatureService.getCandidatureFiles(id);
    }
}
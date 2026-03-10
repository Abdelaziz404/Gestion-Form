package Controller;

import Dto.Person.PersonRequest;
import Dto.Person.PersonResponse;
import Dto.Person.Formateur.FormateurRequest;
import Service.Person.PersonService;
import Service.Person.Formateur.FormateurService;
import Service.Cloud.CloudinaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    private final PersonService personService;
    private final FormateurService formateurService;
    private final CloudinaryService cloudinaryService;

    public PersonController(PersonService personService,
                            FormateurService formateurService,
                            CloudinaryService cloudinaryService) {
        this.personService = personService;
        this.formateurService = formateurService;
        this.cloudinaryService = cloudinaryService;
    }

    // ================= CREATE =================
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<PersonResponse> createPerson(
            @Valid @RequestPart("person") PersonRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestPart(value = "documents", required = false) List<MultipartFile> documents
    ) throws IOException {

        // Upload image principale si fournie
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile);
            request.setImageUrl(imageUrl);
        }

        // Créer la personne
        personService.createPerson(request);
        PersonResponse response = personService.getByEmail(request.getEmail());

        // Si c’est un Formateur, upload documents et créer les enregistrements
        if (request.getRole() != null && request.getRole().name().equals("FORMATEUR") &&
                documents != null && !documents.isEmpty()) {

            List<String> documentUrls = new ArrayList<>();
            for (MultipartFile file : documents) {
                documentUrls.add(cloudinaryService.uploadFile(file));
            }

            FormateurRequest formateurRequest = new FormateurRequest();
            formateurRequest.setSpecialite(((FormateurRequest) request).getSpecialite());
            formateurRequest.setAnneesExperience(((FormateurRequest) request).getAnneesExperience());
            formateurRequest.setSalaire(((FormateurRequest) request).getSalaire());
            formateurRequest.setDocumentUrls(documentUrls);

            formateurService.createFormateurWithPersonId(formateurRequest, response.getId());
        }

        return ResponseEntity.ok(response);
    }

    // ================= UPDATE =================
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<PersonResponse> updatePerson(
            @PathVariable Long id,
            @Valid @RequestPart("person") PersonRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestPart(value = "documents", required = false) List<MultipartFile> documents
    ) throws IOException {

        // Upload nouvelle image principale si fournie
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile);
            request.setImageUrl(imageUrl);
        }

        // Mettre à jour la personne
        personService.updatePerson(id, request);
        PersonResponse response = personService.getById(id);

        // Si c’est un Formateur, upload documents et mettre à jour en DB
        if (request.getRole() != null && request.getRole().name().equals("FORMATEUR") &&
                documents != null && !documents.isEmpty()) {

            List<String> documentUrls = new ArrayList<>();
            for (MultipartFile file : documents) {
                documentUrls.add(cloudinaryService.uploadFile(file));
            }

            FormateurRequest formateurRequest = new FormateurRequest();
            formateurRequest.setSpecialite(((FormateurRequest) request).getSpecialite());
            formateurRequest.setAnneesExperience(((FormateurRequest) request).getAnneesExperience());
            formateurRequest.setSalaire(((FormateurRequest) request).getSalaire());
            formateurRequest.setDocumentUrls(documentUrls);

            formateurService.updateFormateurWithPersonId(id, formateurRequest);
        }

        return ResponseEntity.ok(response);
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> getPersonById(@PathVariable Long id) {
        PersonResponse response = personService.getById(id);
        return ResponseEntity.ok(response);
    }

    // ================= GET BY EMAIL =================
    @GetMapping("/email/{email}")
    public ResponseEntity<PersonResponse> getPersonByEmail(@PathVariable String email) {
        PersonResponse response = personService.getByEmail(email);
        return ResponseEntity.ok(response);
    }

    // ================= GET BY PHONE =================
    @GetMapping("/phone/{phone}")
    public ResponseEntity<PersonResponse> getPersonByPhone(@PathVariable String phone) {
        PersonResponse response = personService.getByPhone(phone);
        return ResponseEntity.ok(response);
    }

    // ================= GET ALL =================
    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAllPersons() {
        List<PersonResponse> list = personService.findAllResponse();
        return ResponseEntity.ok(list);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
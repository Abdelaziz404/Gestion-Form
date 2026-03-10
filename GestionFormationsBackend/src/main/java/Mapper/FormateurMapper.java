package Mapper;

import Dto.Person.Formateur.FormateurRequest;
import Dto.Person.Formateur.FormateurResponse;
import Entity.Document;
import Entity.Formateur;

import java.util.HashSet;
import java.util.Set;


public final class FormateurMapper {

    private FormateurMapper() {}

    // ---------------- Entity <- Request ----------------
    public static Formateur toEntity(FormateurRequest request, Long personId) {

        Formateur formateur = Formateur.builder()
                .id(personId)
                .specialite(request.getSpecialite())
                .anneesExperience(request.getAnneesExperience())
                .salaire(request.getSalaire())
                .build();

        if (request.getDocumentUrls() != null) {
            Set<Document> documents = new HashSet<>();

            for (String url : request.getDocumentUrls()) {
                Document document = Document.builder()
                        .documentUrl(url)
                        .formateur(formateur)
                        .build();
                documents.add(document);
            }

            formateur.setDocuments(documents);
        }

        return formateur;
    }

    // ---------------- Entity -> Response ----------------
    public static FormateurResponse toResponse(Formateur formateur) {

        FormateurResponse response = new FormateurResponse();
        response.setId(formateur.getId());
        response.setPrenom(formateur.getPrenom());
        response.setNom(formateur.getNom());
        response.setEmail(formateur.getEmail());
        response.setTelephone(formateur.getTelephone());
        response.setImgSrc(formateur.getImageUrl());
        response.setRole(formateur.getRole());

        response.setSpecialite(formateur.getSpecialite());
        response.setAnneesExperience(formateur.getAnneesExperience());
        response.setSalaire(formateur.getSalaire());

        if (formateur.getDocuments() != null) {
            response.setDocumentsIds(
                    formateur.getDocuments()
                            .stream()
                            .map(Document::getDossierId)
                            .toList()
            );
        }

        return response;
    }

    // ---------------- Update existing entity ----------------
    public static void updateEntity(Formateur existing, FormateurRequest request) {

        if (request.getSpecialite() != null) {
            existing.setSpecialite(request.getSpecialite());
        }

        if (request.getAnneesExperience() != null) {
            existing.setAnneesExperience(request.getAnneesExperience());
        }

        if (request.getSalaire() != null) {
            existing.setSalaire(request.getSalaire());
        }

        if (request.getDocumentUrls() != null) {
            Set<Document> documents = new HashSet<>();
            for (String url : request.getDocumentUrls()) {
                Document document = Document.builder()
                        .documentUrl(url)
                        .formateur(existing)
                        .build();
                documents.add(document);
            }
            existing.setDocuments(documents);
        }
    }
}
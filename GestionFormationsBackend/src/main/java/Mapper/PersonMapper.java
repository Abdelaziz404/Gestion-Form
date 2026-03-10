package Mapper;

import Dto.Person.PersonRequest;
import Dto.Person.PersonResponse;
import Entity.Person;

public final class PersonMapper {

    private PersonMapper() {
    }

    public static Person toEntity(PersonRequest request, String encodedPassword) {
        return Person.builder()
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .imageUrl(request.getImageUrl())
                .motDePasse(encodedPassword)
                .role(request.getRole())
                .build();
    }

    public static void updateEntityFromRequest(Person existing, PersonRequest request, String encodedPassword) {
        existing.setPrenom(request.getPrenom());
        existing.setNom(request.getNom());
        existing.setEmail(request.getEmail());
        existing.setTelephone(request.getTelephone());
        existing.setImageUrl(request.getImageUrl());
        if (encodedPassword != null && !encodedPassword.isEmpty()) {
            existing.setMotDePasse(encodedPassword);
        }
    }

    public static PersonResponse toResponse(Person person) {
        PersonResponse response = new PersonResponse() {
        };
        response.setId(person.getId());
        response.setPrenom(person.getPrenom());
        response.setNom(person.getNom());
        response.setEmail(person.getEmail());
        response.setTelephone(person.getTelephone());
        response.setImgSrc(person.getImageUrl());
        response.setRole(person.getRole());
        return response;
    }
}
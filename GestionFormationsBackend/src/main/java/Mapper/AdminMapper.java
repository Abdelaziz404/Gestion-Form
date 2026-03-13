package Mapper;

import Dto.Person.Admin.AdminRequest;
import Dto.Person.Admin.AdminResponse;
import Entity.Admin;

public final class AdminMapper {

    private AdminMapper() {}

    // ---------------- Entity <- Request ----------------
    public static Admin toEntity(AdminRequest request, Long personId) {
        return Admin.builder()
                .id(personId)
                // Base fields
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .motDePasse(request.getPassword())
                .imageUrl(request.getImageUrl())
                .role(request.getRole())
                // Admin specific fields
                .permissions(request.getPermissions())
                .build();
    }

    // ---------------- Entity -> Response ----------------
    public static AdminResponse toResponse(Admin admin) {
        // mapper les champs Person via PersonMapper
        AdminResponse response = new AdminResponse();
        response.setId(admin.getId());
        response.setPrenom(admin.getPrenom());
        response.setNom(admin.getNom());
        response.setEmail(admin.getEmail());
        response.setTelephone(admin.getTelephone());
        response.setImgSrc(admin.getImageUrl());
        response.setRole(admin.getRole());

        // mapper les champs Admin spécifiques
        response.setPermissions(admin.getPermissions());
        return response;
    }

    // ---------------- Update existing entity ----------------
    public static void updateEntity(Admin existing, AdminRequest request) {
        if (request.getPermissions() != null) {
            existing.setPermissions(request.getPermissions());
        }
        // si besoin, tu peux ajouter PersonMapper.updateEntity(existing, request, passwordUtil);
    }
}
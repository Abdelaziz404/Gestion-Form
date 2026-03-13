package Service.Person.Admin;

import Dto.Person.Admin.AdminResponse;
import Dto.Person.Admin.AdminRequest;
import Entity.Admin;

import java.util.List;

public interface AdminService {

    // Role-specific methods linked to Person ID
    AdminResponse createAdminWithPersonId(AdminRequest request, Long personId);

    AdminResponse updateAdminWithPersonId(Long personId, AdminRequest request);

    // DTO methods (GET)
    AdminResponse getAdminByPersonId(Long personId);

    AdminResponse getAdminById(Long id);

    List<AdminResponse> getAllAdmins();

    void deleteById(Long id);
}
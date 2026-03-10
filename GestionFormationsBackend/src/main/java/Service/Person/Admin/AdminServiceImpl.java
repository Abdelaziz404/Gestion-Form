package Service.Person.Admin;

import Dto.Person.Admin.AdminResponse;
import Dto.Person.Admin.AdminRequest;
import Entity.Admin;
import Mapper.AdminMapper;
import Repository.AdminRepository;
import Exception.EntityNotFoundException;
import Exception.ValidationException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

        private final AdminRepository adminRepository;

        public AdminServiceImpl(AdminRepository adminRepository) {
                this.adminRepository = adminRepository;
        }

        // ---------------- CREATE ADMIN ----------------
        @Override
        @CacheEvict(value = { "admins", "adminResponses" }, allEntries = true)
        public AdminResponse createAdminWithPersonId(AdminRequest request, Long personId) {
                if (request == null)
                        throw new ValidationException("AdminRequest cannot be null");
                if (personId == null)
                        throw new ValidationException("Person ID cannot be null");

                Admin admin = AdminMapper.toEntity(request, personId);
                adminRepository.save(admin);

                return AdminMapper.toResponse(admin);
        }

        // ---------------- UPDATE ADMIN ----------------
        @Override
        @CacheEvict(value = { "admins", "adminResponses" }, allEntries = true)
        public AdminResponse updateAdminWithPersonId(Long personId, AdminRequest request) {
                if (request == null)
                        throw new ValidationException("AdminRequest cannot be null");
                if (personId == null)
                        throw new ValidationException("Person ID cannot be null");

                Admin existing = adminRepository.findById(personId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Admin not found for personId: " + personId));

                AdminMapper.updateEntity(existing, request);
                adminRepository.save(existing);

                return AdminMapper.toResponse(existing);
        }

        // ---------------- GET ----------------
        @Override
        public AdminResponse getAdminByPersonId(Long personId) {
                Admin admin = adminRepository.findById(personId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Admin not found for personId: " + personId));
                return AdminMapper.toResponse(admin);
        }

        @Override
        public AdminResponse getAdminById(Long id) {
                Admin admin = adminRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));
                return AdminMapper.toResponse(admin);
        }

        @Override
        public List<AdminResponse> getAllAdmins() {
                return adminRepository.findAll().stream()
                        .map(AdminMapper::toResponse)
                        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                                java.util.Collections.reverse(list);
                                return list;
                        }));
        }

        // ---------------- DELETE ----------------
        @Override
        @CacheEvict(value = { "admins", "adminResponses" }, allEntries = true)
        public void deleteById(Long id) {
                Admin existing = adminRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));

                if (existing.isDefault()) {
                        throw new ValidationException("Cannot delete the default admin");
                }

                adminRepository.deleteById(id);
        }

        // ---------------- BASE CRUD ----------------
        @Override
        public Admin save(Admin entity) {
                return adminRepository.save(entity);
        }

        @Override
        public java.util.Optional<Admin> findById(Long id) {
                return adminRepository.findById(id);
        }

        @Override
        public List<Admin> findAll() {
                return adminRepository.findAll();
        }

        @Override
        public boolean existsById(Long id) {
                return adminRepository.existsById(id);
        }
}
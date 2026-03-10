package Service.Person.Admin;

import Dto.Person.Admin.AdminRequest;
import Dto.Person.Admin.AdminResponse;
import Entity.Admin;
import Enum.Role;
import Exception.EntityNotFoundException;
import Repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdminServiceImpl Tests")
class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    private AdminRequest adminRequest;
    private Admin admin;

    @BeforeEach
    void setUp() {
        adminRequest = new AdminRequest();
        adminRequest.setPermissions(1); // Only permissions used in Mapper

        admin = new Admin();
        admin.setId(1L);
        admin.setPermissions(1);
        admin.setRole(Role.ADMIN);
        admin.setEmail("admin@test.com");
    }

    @Test
    @DisplayName("Should create admin successfully")
    void testCreateAdmin_Success() {
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        AdminResponse response = adminService.createAdminWithPersonId(adminRequest, 1L);

        assertNotNull(response);
        assertEquals(admin.getId(), response.getId());
        assertEquals(admin.getPermissions(), response.getPermissions());
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    @DisplayName("Should update admin successfully")
    void testUpdateAdmin_Success() {
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        AdminResponse response = adminService.updateAdminWithPersonId(1L, adminRequest);

        assertNotNull(response);
        assertEquals(admin.getId(), response.getId());
        assertEquals(admin.getPermissions(), response.getPermissions());
        verify(adminRepository, times(1)).findById(1L);
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when admin not found on update")
    void testUpdateAdmin_NotFound_ThrowsException() {
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> adminService.updateAdminWithPersonId(1L, adminRequest));

        verify(adminRepository, never()).save(any(Admin.class));
    }

    @Test
    @DisplayName("Should delete admin successfully")
    void testDeleteAdmin_Success() {
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        adminService.deleteById(1L);

        verify(adminRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when deleting non-existing admin")
    void testDeleteAdmin_NotFound_ThrowsException() {
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> adminService.deleteById(1L));

        verify(adminRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should return admin by personId")
    void testGetAdminByPersonId_Success() {
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        AdminResponse response = adminService.getAdminByPersonId(1L);

        assertNotNull(response);
        assertEquals(admin.getId(), response.getId());
        assertEquals(admin.getPermissions(), response.getPermissions());
        verify(adminRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when getting admin by personId")
    void testGetAdminByPersonId_NotFound() {
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> adminService.getAdminByPersonId(1L));

        verify(adminRepository, times(1)).findById(1L);
    }
}
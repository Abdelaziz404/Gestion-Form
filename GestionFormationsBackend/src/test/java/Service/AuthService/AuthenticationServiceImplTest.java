package Service.AuthService;

import Dto.Auth.AuthRequest;
import Dto.Auth.AuthResponse;
import Entity.Participant;
import Entity.Person;
import Enum.Role;
import Exception.EntityNotFoundException;
import Exception.ValidationException;
import Repository.PersonRepository;
import Security.JwtService;
import Service.Validators.PasswordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthenticationService Tests")
class AuthenticationServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private JwtService jwtService;

    private PasswordEncoder passwordEncoder;
    private AuthenticationServiceImpl authenticationService;

    private AuthRequest authRequest;
    private Person validPerson;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        PasswordValidator.setPasswordEncoder(passwordEncoder);

        authenticationService = new AuthenticationServiceImpl(
                personRepository,
                jwtService);
        authRequest = new AuthRequest();
        authRequest.setEmail("test@example.com");
        authRequest.setPassword("password123");

        validPerson = Participant.builder()
                .id(1L)
                .email("test@example.com")
                .motDePasse(passwordEncoder.encode("password123"))
                .prenom("John")
                .nom("Doe")
                .role(Role.PARTICIPANT)
                .build();
    }

    @Test
    @DisplayName("Should authenticate successfully with valid credentials")
    void testAuthenticate_ValidCredentials_ReturnsAuthResponse() {
        when(personRepository.findByEmail("test@example.com")).thenReturn(Optional.of(validPerson));
        when(jwtService.generateToken(validPerson)).thenReturn("jwt-token");
        when(jwtService.generateRefreshToken(validPerson)).thenReturn("refresh-token");

        AuthResponse response = authenticationService.authenticate(authRequest);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("refresh-token", response.getRefreshToken());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("PARTICIPANT", response.getRole());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals(1L, response.getId());

        verify(personRepository).findByEmail("test@example.com");
        verify(jwtService).generateToken(validPerson);
        verify(jwtService).generateRefreshToken(validPerson);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when email does not exist")
    void testAuthenticate_EmailNotFound_ThrowsException() {
        when(personRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> authenticationService.authenticate(authRequest));

        assertEquals("User not found with email: test@example.com", exception.getMessage());
        verify(personRepository).findByEmail("test@example.com");
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    @DisplayName("Should throw ValidationException when password is incorrect")
    void testAuthenticate_IncorrectPassword_ThrowsException() {
        Person dbPerson = Participant.builder()
                .id(1L)
                .email("test@example.com")
                .motDePasse(passwordEncoder.encode("correctPassword"))
                .prenom("John")
                .nom("Doe")
                .role(Role.PARTICIPANT)
                .build();

        authRequest.setPassword("wrongPassword");

        when(personRepository.findByEmail("test@example.com")).thenReturn(Optional.of(dbPerson));

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> authenticationService.authenticate(authRequest));

        assertEquals("Invalid password", exception.getMessage());
        verify(personRepository).findByEmail("test@example.com");
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    @DisplayName("Should handle all user roles correctly")
    void testAuthenticate_DifferentRoles_HandlesCorrectly() {
        testRoleAuthentication(Role.ADMIN, "ADMIN");
        testRoleAuthentication(Role.PARTICIPANT, "PARTICIPANT");
        testRoleAuthentication(Role.FORMATEUR, "FORMATEUR");
    }

    private void testRoleAuthentication(Role role, String expectedRoleString) {
        Person dbPerson = Participant.builder()
                .id(1L)
                .email("test@example.com")
                .motDePasse(passwordEncoder.encode("password123"))
                .prenom("Test")
                .nom("User")
                .role(role)
                .build();

        when(personRepository.findByEmail("test@example.com")).thenReturn(Optional.of(dbPerson));
        when(jwtService.generateToken(any(Person.class))).thenReturn("jwt-token");
        when(jwtService.generateRefreshToken(any(Person.class))).thenReturn("refresh-token");

        AuthResponse response = authenticationService.authenticate(authRequest);

        assertEquals(expectedRoleString, response.getRole());

        reset(personRepository, jwtService);
    }

    @Test
    @DisplayName("Should refresh token successfully")
    void testRefreshToken_ValidToken_ReturnsNewTokens() {
        // Simuler un refresh token existant
        String oldRefreshToken = "old-refresh-token";

        // L'utilisateur correspondant à ce refresh token
        Person dbPerson = Participant.builder()
                .id(1L)
                .email("test@example.com")
                .motDePasse(passwordEncoder.encode("password123"))
                .prenom("John")
                .nom("Doe")
                .role(Role.PARTICIPANT)
                .build();

        // Simuler les méthodes du JwtService
        when(jwtService.isTokenExpired(oldRefreshToken)).thenReturn(false);
        when(jwtService.extractId(oldRefreshToken)).thenReturn(dbPerson.getId());
        when(personRepository.findById(dbPerson.getId())).thenReturn(Optional.of(dbPerson));
        when(jwtService.generateToken(dbPerson)).thenReturn("new-jwt-token");
        when(jwtService.generateRefreshToken(dbPerson)).thenReturn("new-refresh-token");

        // Appel de la méthode
        AuthResponse response = authenticationService.refreshToken(oldRefreshToken);

        // Vérifications
        assertNotNull(response);
        assertEquals("new-jwt-token", response.getToken());
        assertEquals("new-refresh-token", response.getRefreshToken());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("PARTICIPANT", response.getRole());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals(1L, response.getId());

        verify(jwtService).isTokenExpired(oldRefreshToken);
        verify(jwtService).extractId(oldRefreshToken);
        verify(personRepository).findById(dbPerson.getId());
        verify(jwtService).generateToken(dbPerson);
        verify(jwtService).generateRefreshToken(dbPerson);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException if refresh token expired")
    void testRefreshToken_ExpiredToken_ThrowsException() {
        String expiredToken = "expired-token";

        when(jwtService.isTokenExpired(expiredToken)).thenReturn(true);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> authenticationService.refreshToken(expiredToken));

        assertEquals("Refresh token expired or invalid", exception.getMessage());
        verify(jwtService).isTokenExpired(expiredToken);
        verifyNoMoreInteractions(jwtService);
        verifyNoInteractions(personRepository);
    }
}
package Service.AuthService;

import Dto.Auth.AuthRequest;
import Dto.Auth.AuthResponse;
import Entity.Participant;
import Entity.Person;
import Enum.Role;
import Repository.PersonRepository;
import Security.JwtService;
import Service.Validators.PasswordValidator;
import Exception.EntityNotFoundException;
import Exception.ValidationException;
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
@DisplayName("Authentication Integration Tests")
class AuthenticationIntegrationTest {

        @Mock
        private PersonRepository personRepository;

        @Mock
        private JwtService jwtService;

        private PasswordEncoder passwordEncoder;
        private AuthenticationServiceImpl authenticationService;

        @BeforeEach
        void setUp() {
                passwordEncoder = new BCryptPasswordEncoder();
                PasswordValidator.setPasswordEncoder(passwordEncoder);
                authenticationService = new AuthenticationServiceImpl(
                                personRepository,
                                jwtService);
        }

        @Test
        @DisplayName("Authenticate successfully with valid email and password")
        void testAuthenticate_ValidEmailAndPassword_Success() {
                String rawPassword = "password123";
                String hashedPassword = passwordEncoder.encode(rawPassword);

                Person dbPerson = Participant.builder()
                                .id(1L)
                                .email("test@example.com")
                                .motDePasse(hashedPassword)
                                .prenom("John")
                                .nom("Doe")
                                .role(Role.PARTICIPANT)
                                .build();

                AuthRequest request = new AuthRequest();
                request.setEmail("test@example.com");
                request.setPassword(rawPassword);

                when(personRepository.findByEmail("test@example.com")).thenReturn(Optional.of(dbPerson));
                when(jwtService.generateToken(dbPerson)).thenReturn("jwt-token");
                when(jwtService.generateRefreshToken(dbPerson)).thenReturn("refresh-token");

                AuthResponse response = authenticationService.authenticate(request);

                assertNotNull(response);
                assertEquals("jwt-token", response.getToken());
                assertEquals("refresh-token", response.getRefreshToken());
                assertEquals("test@example.com", response.getEmail());
                assertEquals("PARTICIPANT", response.getRole());
                assertEquals("John", response.getFirstName());
                assertEquals("Doe", response.getLastName());
                assertEquals(1L, response.getId());

                verify(personRepository).findByEmail("test@example.com");
                verify(jwtService).generateToken(dbPerson);
                verify(jwtService).generateRefreshToken(dbPerson);
        }

        @Test
        @DisplayName("Authentication fails when email does not exist")
        void testAuthenticate_EmailNotFound_ThrowsException() {
                AuthRequest request = new AuthRequest();
                request.setEmail("nonexistent@example.com");
                request.setPassword("password123");

                when(personRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

                assertThrows(EntityNotFoundException.class,
                                () -> authenticationService.authenticate(request));

                verify(personRepository).findByEmail("nonexistent@example.com");
                verify(jwtService, never()).generateToken(any());
        }

        @Test
        @DisplayName("Authentication fails when password is incorrect")
        void testAuthenticate_IncorrectPassword_ThrowsException() {
                String correctPassword = "password123";
                String hashedPassword = passwordEncoder.encode(correctPassword);

                Person dbPerson = Participant.builder()
                                .id(1L)
                                .email("test@example.com")
                                .motDePasse(hashedPassword)
                                .prenom("John")
                                .nom("Doe")
                                .role(Role.PARTICIPANT)
                                .build();

                AuthRequest request = new AuthRequest();
                request.setEmail("test@example.com");
                request.setPassword("wrongPassword");

                when(personRepository.findByEmail("test@example.com")).thenReturn(Optional.of(dbPerson));

                // Use the static PasswordValidator
                assertThrows(ValidationException.class,
                                () -> authenticationService.authenticate(request));

                verify(personRepository).findByEmail("test@example.com");
                verify(jwtService, never()).generateToken(any());
        }

        @Test
        @DisplayName("Authentication works correctly for all roles")
        void testAuthenticate_DifferentRoles_HandlesCorrectly() {
                testRoleAuthentication(Role.ADMIN, "ADMIN");
                testRoleAuthentication(Role.PARTICIPANT, "PARTICIPANT");
                testRoleAuthentication(Role.FORMATEUR, "FORMATEUR");
        }

        private void testRoleAuthentication(Role role, String expectedRoleString) {
                String rawPassword = "password123";
                String hashedPassword = passwordEncoder.encode(rawPassword);

                Person dbPerson = Participant.builder()
                                .id(1L)
                                .email("test@example.com")
                                .motDePasse(hashedPassword)
                                .prenom("Test")
                                .nom("User")
                                .role(role)
                                .build();

                AuthRequest request = new AuthRequest();
                request.setEmail("test@example.com");
                request.setPassword(rawPassword);

                when(personRepository.findByEmail("test@example.com")).thenReturn(Optional.of(dbPerson));
                when(jwtService.generateToken(any(Person.class))).thenReturn("jwt-token");
                when(jwtService.generateRefreshToken(any(Person.class))).thenReturn("refresh-token");

                AuthResponse response = authenticationService.authenticate(request);

                assertEquals(expectedRoleString, response.getRole());

                reset(personRepository, jwtService);
        }

        @Test
        @DisplayName("Refresh token successfully returns new tokens")
        void testRefreshToken_ValidToken_Success() {
                String oldRefreshToken = "old-refresh-token";

                // Simuler l'utilisateur correspondant
                Person dbPerson = Participant.builder()
                                .id(1L)
                                .email("test@example.com")
                                .motDePasse(passwordEncoder.encode("password123"))
                                .prenom("John")
                                .nom("Doe")
                                .role(Role.PARTICIPANT)
                                .build();

                // Mock JwtService
                when(jwtService.isTokenExpired(oldRefreshToken)).thenReturn(false);
                when(jwtService.extractId(oldRefreshToken)).thenReturn(dbPerson.getId());
                when(personRepository.findById(dbPerson.getId())).thenReturn(Optional.of(dbPerson));
                when(jwtService.generateToken(dbPerson)).thenReturn("new-jwt-token");
                when(jwtService.generateRefreshToken(dbPerson)).thenReturn("new-refresh-token");

                // Appel de la méthode refreshToken
                AuthResponse response = authenticationService.refreshToken(oldRefreshToken);

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
        @DisplayName("Refresh token fails if token is expired")
        void testRefreshToken_ExpiredToken_ThrowsException() {
                String expiredToken = "expired-refresh-token";

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
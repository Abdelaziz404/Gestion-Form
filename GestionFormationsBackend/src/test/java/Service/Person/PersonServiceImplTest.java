package Service.Person;

import Dto.Person.Participant.ParticipantRequest;
import Dto.Person.PersonRequest;
import Dto.Person.PersonResponse;
import Entity.Person;
import Enum.Role;
import Exception.BusinessRuleException;
import Repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ValidationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private Service.Person.Admin.AdminService adminService;

    @Mock
    private Service.Person.Formateur.FormateurService formateurService;

    @Mock
    private Service.Person.Participant.ParticipantService participantService;

    @Mock
    private Util.PasswordUtil passwordUtil;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private ParticipantRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = mock(ParticipantRequest.class);
        lenient().when(validRequest.getEmail()).thenReturn("test@example.com");
        lenient().when(validRequest.getPassword()).thenReturn("password123");
        lenient().when(validRequest.getRole()).thenReturn(Role.PARTICIPANT);
    }

    @Test
    void create_ShouldReturnPersonResponse_WhenRequestIsValid() {
        // Arrange
        Person savedPerson = new Person();
        savedPerson.setId(1L);
        savedPerson.setEmail("test@example.com");
        savedPerson.setRole(Role.PARTICIPANT);

        when(personRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordUtil.encodePassword("password123")).thenReturn("encodedPassword");
        when(personRepository.save(any(Person.class))).thenReturn(savedPerson);

        // Act
        PersonResponse response = personService.createPerson(validRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("test@example.com", response.getEmail());
        assertEquals(Role.PARTICIPANT, response.getRole());

        verify(personRepository).findByEmail("test@example.com");
        verify(passwordUtil).encodePassword("password123");
        verify(personRepository).save(any(Person.class));
        verify(participantService).createParticipantWithPersonId(any(ParticipantRequest.class), eq(1L));
    }

    @Test
    void create_ShouldThrowValidationException_WhenRequestIsNull() {
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class,
                () -> personService.createPerson(null));

        assertEquals("PersonRequest cannot be null", exception.getMessage());
        verifyNoInteractions(personRepository, passwordUtil, participantService);
    }

    @Test
    void create_ShouldThrowBusinessRuleException_WhenEmailAlreadyExists() {
        // Arrange
        when(personRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new Person()));

        // Act & Assert
        assertThrows(BusinessRuleException.class, () -> personService.createPerson(validRequest));

        verify(personRepository).findByEmail("test@example.com");
        verifyNoInteractions(passwordUtil, participantService);
    }
}
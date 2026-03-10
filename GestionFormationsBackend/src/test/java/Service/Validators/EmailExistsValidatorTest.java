package Service.Validators;

import Exception.BusinessRuleException;
import Repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmailExistsValidator Tests")
class EmailExistsValidatorTest {

    @Mock
    private PersonRepository personRepository;

    private String email = "test@example.com";

    @Test
    @DisplayName("Should pass validation when email does not exist")
    void testValidate_EmailDoesNotExist_Passes() {
        // Arrange
        when(personRepository.existsByEmail(email)).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> EmailExistsValidator.validate(personRepository, email));

        verify(personRepository).existsByEmail(email);
    }

    @Test
    @DisplayName("Should throw BusinessRuleException when email exists")
    void testValidate_EmailExists_ThrowsException() {
        // Arrange
        when(personRepository.existsByEmail(email)).thenReturn(true);

        // Act & Assert
        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> EmailExistsValidator.validate(personRepository, email));

        assertEquals("Email already exists: " + email, exception.getMessage());
        verify(personRepository).existsByEmail(email);
    }
}
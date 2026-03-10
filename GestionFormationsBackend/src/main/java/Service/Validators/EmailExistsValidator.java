package Service.Validators;

import Exception.BusinessRuleException;
import Repository.PersonRepository;

public final class EmailExistsValidator {

    private EmailExistsValidator() {} // prevent instantiation

    public static void validate(PersonRepository personRepository, String email) {
        if (personRepository.existsByEmail(email)) {
            throw new BusinessRuleException("Email already exists: " + email);
        }
    }
}
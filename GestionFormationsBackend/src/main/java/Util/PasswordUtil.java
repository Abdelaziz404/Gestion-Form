package Util;

import Entity.Person;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public final class PasswordUtil {

    private final PasswordEncoder passwordEncoder;

    public PasswordUtil(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Encode a raw password using the configured PasswordEncoder
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Verify if raw password matches the encoded password
     */
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Encode a raw password and set it to the given person
     */
    public void encodePasswordForPerson(Person person, String rawPassword) {
        if (person == null || rawPassword == null) return;
        person.setMotDePasse(encodePassword(rawPassword));
    }
}
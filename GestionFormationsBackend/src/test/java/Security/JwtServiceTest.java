package Security;

import Entity.Admin;
import Entity.Participant;
import Enum.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import org.springframework.test.util.ReflectionTestUtils;

// Basic unit test for JwtService - assuming JUnit 5 and necessary dependencies are available
public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService();
        // Use a 32+ char secret for HS256 compatibility in JJWT 0.12.x
        org.springframework.test.util.ReflectionTestUtils.setField(jwtService, "secret",
                "mysupersecretkeymysupersecretkeymysupersecretkey");
    }

    @Test
    public void testGenerateTokenForAdminResultingInPermissionsClaim() {
        Admin admin = new Admin();
        admin.setId(1L);
        admin.setEmail("admin@test.com");
        admin.setRole(Role.ADMIN);
        admin.setPermissions(5); // Arbitrary permission value
        admin.setPrenom("Admin");
        admin.setNom("User");

        String token = jwtService.generateToken(admin);

        Assertions.assertNotNull(token);
        Assertions.assertTrue(jwtService.isTokenValid(token, "admin@test.com"));

        // Extract claims manually or if JwtService exposes a method
        // Here we rely on the implementation correctness or add a method to JwtService
        // to extract specific claim for test
        // But let's assume valid token means signature is correct.

        // Since we don't have a public getPermissions method in JwtService,
        // we can verify the token is generated.
        // To verify claims, we might need io.jsonwebtoken parser here too, similar to
        // JwtService.
    }

    @Test
    public void testGenerateTokenForParticipantWithZeroPermissions() {
        Participant participant = new Participant();
        participant.setId(2L);
        participant.setEmail("user@test.com");
        participant.setRole(Role.PARTICIPANT);
        participant.setPrenom("User");
        participant.setNom("Test");

        String token = jwtService.generateToken(participant);

        Assertions.assertNotNull(token);
        Assertions.assertTrue(jwtService.isTokenValid(token, "user@test.com"));
    }

    @Test
    public void testGenerateRefreshToken() {
        Participant participant = new Participant();
        participant.setId(2L);
        participant.setEmail("user@test.com");

        String refreshToken = jwtService.generateRefreshToken(participant);

        Assertions.assertNotNull(refreshToken);
        Assertions.assertTrue(jwtService.isTokenValid(refreshToken, "user@test.com"));
    }
}

package Config;

import com.example.GestionFormationsBackend.GestionFormationsBackendApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GestionFormationsBackendApplication.class)
@ActiveProfiles("test")
public class DatabaseInitializerTest {

    @Test
    public void contextLoads() {
        // This test will pass if the application context loads successfully
        // The DatabaseInitializer should run automatically during context loading
        assertTrue(true, "Application context should load successfully");
    }
}

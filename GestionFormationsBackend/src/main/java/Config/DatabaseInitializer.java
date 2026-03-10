package Config;

import Entity.*;
import Enum.Role;
import Enum.StatusFormation;
import Repository.*;
import Util.DefaultAdminConfig;
import Util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private FormateurRepository formateurRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists - optimized check (O(1))
        if (adminRepository.findFirstByOrderByIdAsc().isPresent()) {
            System.out.println("Database already initialized. Skipping data initialization.");
            return;
        }

        System.out.println("Initializing database with default data...");

        Admin admin = Admin.builder()
                .prenom(DefaultAdminConfig.PRENOM)
                .nom(DefaultAdminConfig.NOM)
                .email(DefaultAdminConfig.EMAIL)
                .imageUrl(DefaultAdminConfig.IMAGE_URL)
                .role(Role.ADMIN)
                .permissions(DefaultAdminConfig.ALL_PERMISSIONS)
                .motDePasse(DefaultAdminConfig.PASSWORD)// temporary, will encode next
                .isDefault(true)
                .build();

        passwordUtil.encodePasswordForPerson(admin, DefaultAdminConfig.PASSWORD);

        adminRepository.save(admin);

        System.out.println("Created default admin: " + DefaultAdminConfig.EMAIL);


    }
}
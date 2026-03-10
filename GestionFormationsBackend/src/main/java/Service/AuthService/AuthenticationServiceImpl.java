package Service.AuthService;

import Dto.Auth.AuthRequest;
import Dto.Auth.AuthResponse;
import Entity.Admin;
import Entity.Person;
import Exception.EntityNotFoundException;
import Exception.ValidationException;
import Repository.PersonRepository;
import Security.JwtService;
import Util.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PersonRepository personRepository;
    private final JwtService jwtService;
    private final PasswordUtil passwordUtil;

    public AuthenticationServiceImpl(PersonRepository personRepository,
                                     JwtService jwtService,
                                     PasswordUtil passwordUtil) {
        this.personRepository = personRepository;
        this.jwtService = jwtService;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {

        Person person = personRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with email: " + request.getEmail()));

        if (!passwordUtil.verifyPassword(request.getPassword(), person.getMotDePasse())) {
            throw new ValidationException("Invalid password");
        }

        String token = jwtService.generateToken(person);
        String refreshToken = jwtService.generateRefreshToken(person);

        Integer permissions = null; // default null for non-admins
        if (person instanceof Admin) {
            permissions = ((Admin) person).getPermissions();
        }

        return AuthResponse.builder()
                .id(person.getId())
                .firstName(person.getPrenom())
                .lastName(person.getNom())
                .email(person.getEmail())
                .role(person.getRole().name())
                .token(token)
                .refreshToken(refreshToken)
                .permissions(permissions)
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {

        if (jwtService.isTokenExpired(refreshToken)) {
            throw new EntityNotFoundException("Refresh token expired or invalid");
        }

        Long personId = jwtService.extractId(refreshToken);

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + personId));

        String newToken = jwtService.generateToken(person);
        String newRefreshToken = jwtService.generateRefreshToken(person);

        Integer permissions = null; // default null for non-admins
        if (person instanceof Admin) {
            permissions = ((Admin) person).getPermissions();
        }

        return AuthResponse.builder()
                .id(person.getId())
                .firstName(person.getPrenom())
                .lastName(person.getNom())
                .email(person.getEmail())
                .role(person.getRole().name())
                .token(newToken)
                .refreshToken(newRefreshToken)
                .permissions(permissions)
                .build();
    }
}
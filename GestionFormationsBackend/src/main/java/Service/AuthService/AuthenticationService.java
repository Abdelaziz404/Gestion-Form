package Service.AuthService;

import Dto.Auth.AuthRequest;
import Dto.Auth.AuthResponse;

public interface AuthenticationService {

    AuthResponse authenticate(AuthRequest request);

    AuthResponse refreshToken(String refreshToken);

}

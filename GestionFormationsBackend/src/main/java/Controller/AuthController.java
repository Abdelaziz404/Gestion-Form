package Controller;

import Dto.Auth.AuthRequest;
import Dto.Auth.AuthResponse;
import Service.AuthService.AuthenticationService;
import Util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final CookieUtil cookieUtil;

    public AuthController(AuthenticationService authenticationService, CookieUtil cookieUtil) {
        this.authenticationService = authenticationService;
        this.cookieUtil = cookieUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @Valid @RequestBody AuthRequest request,
            HttpServletResponse response) {

        AuthResponse authResponse = authenticationService.authenticate(request);

        cookieUtil.createAuthCookies(response, authResponse.getToken(), authResponse.getRefreshToken());

        // Remove tokens from body for security
        authResponse.setToken(null);
        authResponse.setRefreshToken(null);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        cookieUtil.clearAuthCookies(response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            return ResponseEntity.badRequest().build();
        }

        AuthResponse authResponse = authenticationService.refreshToken(refreshToken);

        cookieUtil.createAuthCookies(response, authResponse.getToken(), authResponse.getRefreshToken());

        authResponse.setToken(null);
        authResponse.setRefreshToken(null);

        return ResponseEntity.ok(authResponse);
    }
}
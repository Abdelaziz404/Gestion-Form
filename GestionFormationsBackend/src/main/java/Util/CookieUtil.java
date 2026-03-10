package Util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public void createAuthCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        addCookie(response, "accessToken", accessToken, (int) Constants.EXPIRATION / 1000);
        addCookie(response, "refreshToken", refreshToken, (int) Constants.REFRESH_EXPIRATION / 1000);
    }

    public void clearAuthCookies(HttpServletResponse response) {
        addCookie(response, "accessToken", null, 0);
        addCookie(response, "refreshToken", null, 0);
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}

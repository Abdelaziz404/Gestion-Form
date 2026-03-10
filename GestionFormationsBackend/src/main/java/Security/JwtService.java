package Security;

import Entity.Person;
import Entity.Admin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Person person) {

        int permissions = 0;

        if (person instanceof Admin admin) {
            permissions = admin.getPermissions();
        }

        return Jwts.builder()
                .subject(person.getEmail())
                .claim("id", person.getId())
                .claim("role", person.getRole().name())
                .claim("permissions", permissions)
                .claim("prenom", person.getPrenom())
                .claim("nom", person.getNom())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Util.Constants.EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken(Person person) {
        return Jwts.builder()
                .subject(person.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Util.Constants.REFRESH_EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public Long extractId(String token) {
        return extractClaims(token).get("id", Long.class);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, String username) {

        final String extractedUsername = extractEmail(token);

        return extractedUsername.equals(username) && !isTokenExpired(token);
    }
}
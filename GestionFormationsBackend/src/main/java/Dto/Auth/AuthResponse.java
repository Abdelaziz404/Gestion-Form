package Dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String refreshToken;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private Integer permissions; // changed from int to Integer
    private Long id;
}
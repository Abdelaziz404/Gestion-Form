package Dto.Auth;

import Util.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {

    @NotBlank(message = ValidationConstants.NOT_BLANK_ERROR_MSG)
    @Email(message = ValidationConstants.EMAIL_ERROR_MSG)
    private String email;

    @NotBlank(message = ValidationConstants.NOT_BLANK_ERROR_MSG)
    private String password;
}
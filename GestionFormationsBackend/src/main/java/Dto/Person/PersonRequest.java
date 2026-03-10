package Dto.Person;

import Util.ValidationConstants;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import Enum.Role;

@Data
public abstract class PersonRequest {

    @NotBlank(message = ValidationConstants.NOT_BLANK_ERROR_MSG)
    @Pattern(regexp = ValidationConstants.NAME_REGEX, message = ValidationConstants.NAME_ERROR_MSG)
    @Size(min = 2, max = 50)
    protected String prenom;

    @NotBlank(message = ValidationConstants.NOT_BLANK_ERROR_MSG)
    @Pattern(regexp = ValidationConstants.NAME_REGEX, message = ValidationConstants.NAME_ERROR_MSG)
    @Size(min = 2, max = 50)
    protected String nom;

    @NotBlank(message = ValidationConstants.NOT_BLANK_ERROR_MSG)
    @Email(message = ValidationConstants.EMAIL_ERROR_MSG)
    protected String email;

    @NotBlank(message = ValidationConstants.NOT_BLANK_ERROR_MSG)
    @Pattern(regexp = ValidationConstants.PASSWORD_REGEX, message = ValidationConstants.PASSWORD_ERROR_MSG)
    protected String password;

    @Pattern(regexp = ValidationConstants.PHONE_REGEX, message = ValidationConstants.PHONE_ERROR_MSG)
    protected String telephone;

    @URL(message = ValidationConstants.URL_ERROR_MSG)
    protected String imageUrl;

    @NotNull
    protected Role role;
}
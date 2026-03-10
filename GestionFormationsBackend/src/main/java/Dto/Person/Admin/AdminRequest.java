package Dto.Person.Admin;

import Dto.Person.PersonRequest;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdminRequest extends PersonRequest {

    @NotNull
    @Min(0)
    private Integer permissions;
}
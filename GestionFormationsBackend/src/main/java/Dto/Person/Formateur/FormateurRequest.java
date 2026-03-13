package Dto.Person.Formateur;

import Dto.Person.PersonRequest;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FormateurRequest extends PersonRequest {

    @NotBlank
    private String specialite;

    @NotNull
    @Min(0)
    private Integer anneesExperience;

    @NotNull
    @DecimalMin("0.0")
    private Double salaire;

    private List<String> documentUrls;

}
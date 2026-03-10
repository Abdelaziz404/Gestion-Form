package Dto.Person.Formateur;

import Dto.Person.PersonResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FormateurResponse extends PersonResponse {

    private String specialite;

    private Integer anneesExperience;

    private Double salaire;

    private List<Long> documentsIds;
}
package Dto.Person;

import Enum.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PersonResponse {

    protected Long id;

    protected String prenom;

    protected String nom;

    protected String email;

    protected String telephone;

    protected String imgSrc;

    protected Role role;
}
package Dto.Person.Participant;

import Dto.Person.PersonRequest;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ParticipantRequest extends PersonRequest {

    @NotNull
    private LocalDate dateInscription;
}
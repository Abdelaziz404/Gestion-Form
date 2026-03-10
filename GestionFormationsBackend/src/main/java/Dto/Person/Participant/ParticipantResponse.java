package Dto.Person.Participant;

import Dto.Person.PersonResponse;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantResponse extends PersonResponse {

    private LocalDate dateInscription;
}
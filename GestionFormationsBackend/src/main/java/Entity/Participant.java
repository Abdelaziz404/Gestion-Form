package Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "participants")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Participant extends Person {

    @Column(name = "date_inscription")
    private LocalDate dateInscription;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecuPayment> recuPayments = new ArrayList<>();
}

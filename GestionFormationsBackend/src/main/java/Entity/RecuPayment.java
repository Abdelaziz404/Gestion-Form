package Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recus_payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecuPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recu_payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inscription_id", nullable = false)
    private Inscription inscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    @Column(name = "recu_payment_source")
    private String recuPaymentSource;
}

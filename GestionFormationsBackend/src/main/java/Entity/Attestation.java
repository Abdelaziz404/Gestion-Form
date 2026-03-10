package Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attestations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attestation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inscription_id", nullable = false)
    private Inscription inscription;

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(name = "statut_attestation")
    private String statutAttestation; // e.g., "VALIDATED", "PENDING", "REJECTED"
}
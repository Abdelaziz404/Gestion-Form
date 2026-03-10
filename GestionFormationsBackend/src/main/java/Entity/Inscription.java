package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inscription_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;

    @Column(name = "date_inscription", nullable = false)
    private LocalDateTime dateInscription;

    @Column(name = "statut_inscription")
    private String statutInscription; // e.g., "CONFIRMED", "PENDING", "CANCELLED"

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    // Automatically set creation timestamp
    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
        // dateModification stays null on creation
    }

    // Automatically update modification timestamp
    @PreUpdate
    protected void onUpdate() {
        this.dateModification = LocalDateTime.now();
    }

}
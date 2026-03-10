package Entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import Enum.StatusFormation;

@Entity
@Table(name = "formations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "formation_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "prix", nullable = false, precision = 10, scale = 2)
    private BigDecimal prix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formateur_id", nullable = false)
    private Formateur formateur;

    @Column(name = "nombre_participant_min", nullable = false)
    private Integer nombreParticipantMin;

    @Column(name = "nombre_participant_inscrit", nullable = false)
    private Integer nombreParticipantInscrit;

    @Column(name = "date_creation", updatable = false, nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(name = "date_debut_cours")
    private LocalDateTime dateDebutCours;

    @Enumerated(EnumType.STRING)
    @Column(name = "est_disponible", nullable = false)
    private StatusFormation status;

    @Column(name = "image_url")
    protected String imageUrl;

    // ---------------- Relationships ----------------
    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Inscription> inscriptions = new HashSet<>();

    // ---------------- Lifecycle ----------------
    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModification = LocalDateTime.now();
    }
}
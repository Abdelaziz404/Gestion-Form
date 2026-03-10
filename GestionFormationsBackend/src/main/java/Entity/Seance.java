package Entity;

import Enum.StatusFormation;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import Enum.StatusFormation;

@Entity
@Table(name = "seances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seance_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;

    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "duree_minutes")
    private Integer dureeMinutes;

    @Column(name = "status_seance")
    @Enumerated(EnumType.STRING)
    private StatusFormation status;

    @Column(name = "nombre_etudiant_present")
    private Integer nombreEtudiantPresent;

    @Column(name = "nombre_etudiant_inscrit")
    private Integer nombreEtudiantInscrit;

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;
}
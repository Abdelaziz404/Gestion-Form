package Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Enum.StatutInscription;

@Entity
@Table(name = "candidatures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Candidature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Personal info
    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String motDePasse = "";

    // Professional info
    @Column(nullable = false)
    private Integer anneesExperience;

    @Column(nullable = false)
    private String specialites;

    // List of file URLs (PDFs uploaded)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "candidature_files", joinColumns = @JoinColumn(name = "candidature_id"))
    @Column(name = "file_url")
    private List<String> files = new ArrayList<>();

    // Status of candidature
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutInscription status;

    // Timestamps
    @Column(name = "date_creation", updatable = false)
    private LocalDate dateCreation;

    @Column(name = "date_modification")
    private LocalDate dateModification;

    @PrePersist
    protected void onCreate() { this.dateCreation = LocalDate.now(); }

    @PreUpdate
    protected void onUpdate() { this.dateModification = LocalDate.now(); }
}
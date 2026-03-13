package Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "formateurs")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Formateur extends Person {

    @Column(name = "specialite")
    private String specialite;

    @Column(name = "annees_experience")
    private Integer anneesExperience;

    @Column(name = "salaire", nullable = false, columnDefinition = "DOUBLE DEFAULT 5000")
    private Double salaire;

    // Un formateur peut avoir plusieurs documents
    @OneToMany(mappedBy = "formateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Document> documents = new HashSet<>();

    // Méthode utilitaire pour ajouter un document facilement
    public void addDocument(Document doc) {
        documents.add(doc);
        doc.setFormateur(this);
    }
}
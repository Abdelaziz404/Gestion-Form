package Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Column(name = "salaire")
    private Double salaire;

    @OneToMany(mappedBy = "formateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Document> documents = new HashSet<>();
}
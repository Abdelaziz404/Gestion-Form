package Entity;

import Enum.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "prenom", nullable = false)
    protected String prenom;

    @Column(name = "nom", nullable = false)
    protected String nom;

    @Column(name = "email", unique = true, nullable = false)
    protected String email;

    @Column(name = "telephone")
    protected String telephone;

    @Column(name = "mot_de_passe", nullable = false)
    protected String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    protected Role role;

    @Column(name = "image_url")
    protected String imageUrl;

    @Column(name = "date_creation", updatable = false)
    protected LocalDate dateCreation;

    @Column(name = "date_modification")
    protected LocalDate dateModification;

    // Automatically set creation date
    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDate.now();
    }

    // Automatically update modification date
    @PreUpdate
    protected void onUpdate() {
        this.dateModification = LocalDate.now();
    }

    // Computed full name (NOT stored in DB)
    @Transient
    public String getFullName() {
        return prenom + " " + nom;
    }
}
package Repository;

import Entity.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    // Cherche une candidature par email
    Optional<Candidature> findByEmail(String email);
}

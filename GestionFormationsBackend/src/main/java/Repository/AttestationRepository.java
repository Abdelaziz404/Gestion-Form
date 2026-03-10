package Repository;

import Entity.Attestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttestationRepository extends JpaRepository<Attestation, Long> {
    // Attestation-specific query methods can be added here
}
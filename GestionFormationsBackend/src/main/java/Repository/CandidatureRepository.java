package Repository;

import Entity.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

}

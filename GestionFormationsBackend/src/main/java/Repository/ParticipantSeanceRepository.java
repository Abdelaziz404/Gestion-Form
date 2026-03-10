package Repository;

import Entity.ParticipantSeance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantSeanceRepository extends JpaRepository<ParticipantSeance, Long> {
    // ParticipantSeance-specific query methods can be added here
}
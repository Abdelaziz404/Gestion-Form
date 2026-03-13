package Repository;

import Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);
    Optional<Person> findByEmailIgnoreCase(String email);

    boolean existsByEmail(String email);

    Optional<Person> findByTelephone(String telephone);
}
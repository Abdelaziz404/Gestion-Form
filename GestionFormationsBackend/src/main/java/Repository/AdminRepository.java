package Repository;

import Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Find admin by email
    Optional<Admin> findByEmail(String email);

    // Check if an admin exists by email (optimized query)
    boolean existsByEmail(String email);

    // Get the first admin (by ID ascending)
    Optional<Admin> findFirstByOrderByIdAsc();

    // Optional: find by phone if needed
    Optional<Admin> findByTelephone(String telephone);
}
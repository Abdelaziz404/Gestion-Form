package Service.Validators;

import Exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public final class EntityExistsByIdValidator {

    private EntityExistsByIdValidator() {} // prevent instantiation

    public static <T, ID> void validate(JpaRepository<T, ID> repository, ID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
    }
}
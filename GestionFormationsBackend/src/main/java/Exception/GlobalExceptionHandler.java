package Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ======================
    // 400 - Bad Request
    // ======================

    @ExceptionHandler({
            BusinessRuleException.class,
            ValidationException.class
    })
    public ResponseEntity<Object> handleBadRequest(RuntimeException ex) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }

    // ======================
    // 404 - Not Found
    // ======================

    @ExceptionHandler({
            EntityNotFoundException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<Object> handleNotFound(RuntimeException ex) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    // ======================
    // 409 - Conflict
    // ======================

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<Object> handleDuplicate(DuplicateEntityException ex) {

        return buildResponse(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
    }

    // ======================
    // 401 - Unauthorized
    // ======================

    @ExceptionHandler({
            InvalidCredentialsException.class,
            BadCredentialsException.class
    })
    public ResponseEntity<Object> handleUnauthorized(RuntimeException ex) {

        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                "Invalid email or password"
        );
    }

    // ======================
    // Validation @Valid
    // ======================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        System.err.println("Validation Error in Backend: " + errors);

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Erreur de validation: " + errors.toString()
        );
    }

    // ======================
    // 500 - Internal Server
    // ======================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOther(Exception ex) {
        System.err.println("INTERNAL SERVER ERROR in Backend:");
        ex.printStackTrace();
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error: " + ex.getMessage()
        );
    }

    // ======================
    // Helper
    // ======================

    private ResponseEntity<Object> buildResponse(
            HttpStatus status,
            String message) {

        Map<String, Object> body = new HashMap<>();

        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return ResponseEntity
                .status(status)
                .body(body);
    }
}

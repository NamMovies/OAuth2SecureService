package antonBurshteyn.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.context.support.DefaultMessageSourceResolvable;


import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private record ErrorResponse(String message, int status) {}

    private ResponseEntity<Object> buildErrorResponse(HttpServletRequest request, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ErrorResponse(message, status.value()));
    }

    // 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        logger.warn("Validation failed: {}", errorMessage);
        return buildErrorResponse(request, errorMessage, HttpStatus.BAD_REQUEST);
    }

    // 400
    @ExceptionHandler(UserValidationExceptions.class)
    public ResponseEntity<Object> handleUserValidation(UserValidationExceptions ex, HttpServletRequest request) {
        logger.warn("Invalid {}: {}", ex.getField(), ex.getMessage());
        return buildErrorResponse(request, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 401
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        logger.warn("Bad credentials: {}", ex.getMessage());
        return buildErrorResponse(request, "Invalid email or password", HttpStatus.UNAUTHORIZED);
    }

    // 403
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
        logger.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("Access denied", HttpStatus.FORBIDDEN.value()));
    }

    // 404
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UsernameNotFoundException ex, HttpServletRequest request) {
        logger.warn("User not found: {}", ex.getMessage());
        return buildErrorResponse(request, "User not found", HttpStatus.NOT_FOUND);
    }

    // 409
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest request) {
        logger.warn("User already exists: {}", ex.getMessage());
        return buildErrorResponse(request, ex.getMessage(), HttpStatus.CONFLICT);
    }

    // 409
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Object> handleDatabaseException(DatabaseException ex, HttpServletRequest request) {
        logger.error("Database exception: {}", ex.getMessage());
        return buildErrorResponse(request, "Database error: " + ex.getMessage(), HttpStatus.CONFLICT);
    }

    // 503
    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<Object> handleDatabaseConnection(DatabaseConnectionException ex, HttpServletRequest request) {
        logger.error("Database connection error", ex);
        return buildErrorResponse(request, "Database temporarily unavailable", HttpStatus.SERVICE_UNAVAILABLE);
    }

    // 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOther(Exception ex, HttpServletRequest request) {
        logger.error("Unhandled exception: ", ex);
        return buildErrorResponse(request, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


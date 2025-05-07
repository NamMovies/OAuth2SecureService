package antonBurshteyn.exceptions;

public class DatabaseConnectionException extends RuntimeException {
    private final String email;

    public DatabaseConnectionException(String email, Throwable cause) {
        super("Database connection error for user: " + email, cause);
        this.email = email;
    }
}

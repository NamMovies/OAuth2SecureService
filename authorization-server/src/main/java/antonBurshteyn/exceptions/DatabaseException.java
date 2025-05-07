package antonBurshteyn.exceptions;

public class DatabaseException extends RuntimeException {
    private final String email;

    public DatabaseException(String email, Throwable cause) {
        super("Database error for user: " + email, cause);
        this.email = email;
    }
}
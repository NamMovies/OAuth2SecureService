package antonBurshteyn.exceptions;


public class UserValidationExceptions extends RuntimeException {
    private final String field;

    public UserValidationExceptions(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public UserValidationExceptions(String field) {
        this.field = field;
    }

    public static UserValidationExceptions firstName() {
        return new UserValidationExceptions("firstName", "First name cannot be empty");
    }

    public static UserValidationExceptions lastName() {
        return new UserValidationExceptions("lastName", "Last name cannot be empty");
    }

    public static UserValidationExceptions email(String email) {
        return new UserValidationExceptions("email", "Invalid email format: " + email);
    }

    public static UserValidationExceptions password(String details) {
        return new UserValidationExceptions("password", "Password validation failed: " + details);
    }
}
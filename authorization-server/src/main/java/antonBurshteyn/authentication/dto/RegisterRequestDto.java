package antonBurshteyn.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

public class RegisterRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1743655306090677377L;

    @Schema(example = "Anton")
    private String firstName;

    @Schema(example = "Burshteyn")
    private String lastName;

    @Schema(example = "anton.burshteyn@example.com")
    private String email;

    @Schema(example = "StrongPass123!")
    private String password;

    public RegisterRequestDto() {}

    public RegisterRequestDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "RegisterRequestDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

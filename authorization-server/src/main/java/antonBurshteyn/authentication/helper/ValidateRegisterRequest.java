package antonBurshteyn.authentication.helper;

import antonBurshteyn.authentication.dto.RegisterRequestDto;

public interface ValidateRegisterRequest {
    void validateFirstName(String firstName);

    void validateLastName(String lastName);

    void isEmailValid(String email);

    void isValidPassword(String password);

    void checkCredentials(RegisterRequestDto registerRequest);
}
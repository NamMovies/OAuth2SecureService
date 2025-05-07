package antonBurshteyn.authentication.helper;

import antonBurshteyn.authentication.dto.RegisterRequestDto;
import antonBurshteyn.authentication.repository.UserRepository;
import antonBurshteyn.exceptions.UserAlreadyExistsException;
import antonBurshteyn.exceptions.UserValidationExceptions;
import org.passay.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ValidateRegisterRequestImpl implements ValidateRegisterRequest{

    private final UserRepository userRepository;

    public ValidateRegisterRequestImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void validateFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw UserValidationExceptions.firstName();
        }
    }

    public void validateLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw UserValidationExceptions.lastName();
        }
    }

    public void isEmailValid(String email) {
        boolean isValid = email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        if (!isValid) {
            throw UserValidationExceptions.email(email);
        }
    }

    public void isValidPassword(String password) {
        PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        ));

        RuleResult result = passwordValidator.validate(new PasswordData(password));
        if (!result.isValid()) {
            String errorMessage = String.join(", ", passwordValidator.getMessages(result));
            throw UserValidationExceptions.password(errorMessage);
        }
    }

    public void checkCredentials(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + registerRequestDto.getEmail() + " already exists");
        }

        validateFirstName(registerRequestDto.getFirstName());
        validateLastName(registerRequestDto.getLastName());
        isEmailValid(registerRequestDto.getEmail());
        isValidPassword(registerRequestDto.getPassword());
    }

}
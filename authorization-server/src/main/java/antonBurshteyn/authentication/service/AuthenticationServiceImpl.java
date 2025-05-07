package antonBurshteyn.authentication.service;

import antonBurshteyn.authentication.dto.RegisterRequestDto;
import antonBurshteyn.authentication.helper.ValidateRegisterRequest;
import antonBurshteyn.authentication.repository.UserRepository;
import antonBurshteyn.enums.Provider;
import antonBurshteyn.exceptions.DatabaseConnectionException;
import antonBurshteyn.exceptions.DatabaseException;
import antonBurshteyn.exceptions.InternalServerErrorException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.QueryTimeoutException;
import antonBurshteyn.authentication.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidateRegisterRequest validateRegisterRequest;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ValidateRegisterRequest validateRegisterRequest) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validateRegisterRequest = validateRegisterRequest;
    }

    @Override
    @Transactional
    public User register(RegisterRequestDto request) {
        String userEmail = request.getEmail();

        logger.info("Starting registration for user: {}", userEmail);
        validateRegisterRequest.checkCredentials(request);

        try {
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(userEmail);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("USER");
            user.setProvider(Provider.LOCAL);
            user.setEnabled(true);

            userRepository.save(user);
            return user;

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(userEmail, e);
        } catch (QueryTimeoutException | CannotAcquireLockException e) {
            throw new DatabaseConnectionException(userEmail, e);
        } catch (Exception e) {
            logger.error("Unexpected error registering user {}", userEmail, e);
            throw new InternalServerErrorException("Registration failed", e);
        }
    }

}
package antonBurshteyn.config;

import antonBurshteyn.authentication.entity.User;
import antonBurshteyn.authentication.repository.UserRepository;
import antonBurshteyn.enums.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${DEFAULT_ADMIN_PASSWORD}")
    private String adminPassword;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String adminEmail = "admin@example.com";

        if (userRepository.existsByEmail(adminEmail)) {
            logger.info("Admin already exists: {}", adminEmail);
            return;
        }

        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("Account");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole("ADMIN");
        admin.setProvider(Provider.LOCAL);
        admin.setEnabled(true);

        userRepository.save(admin);
        logger.info("Admin account created: {}", adminEmail);
    }
}

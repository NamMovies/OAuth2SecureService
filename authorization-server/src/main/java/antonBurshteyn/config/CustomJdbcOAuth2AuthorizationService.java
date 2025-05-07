package antonBurshteyn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

public class CustomJdbcOAuth2AuthorizationService extends JdbcOAuth2AuthorizationService {
    public CustomJdbcOAuth2AuthorizationService(
            JdbcOperations jdbcOperations,
            RegisteredClientRepository registeredClientRepository,
            ObjectMapper objectMapper) {
        super(jdbcOperations, registeredClientRepository);
    }
}
package antonBurshteyn.config;

import antonBurshteyn.authentication.service.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class TokenCustomizerConfig {

    private static final Logger logger = LoggerFactory.getLogger(TokenCustomizerConfig.class);

    @Bean
    @Primary
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(UserDetailsService userDetailsService) {
        return context -> {
            String tokenType = context.getTokenType().getValue();
            Authentication principal = context.getPrincipal();

            logger.info("TokenCustomizer invoked for token type: {}", tokenType);
            logger.debug("Principal class: {}", principal.getPrincipal().getClass().getName());
            logger.debug("Claims before customization: {}", context.getClaims().build());

            if ("access_token".equals(tokenType)) {
                if (principal.getPrincipal() instanceof CustomUserDetails customUserDetails) {
                    var user = customUserDetails.getUser();
                    context.getClaims().claims(claims -> {
                        claims.put("authorities", customUserDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority).toList());
                        claims.put("user_id", user.getId());
                        claims.put("provider", user.getProvider());
                        claims.put("email", user.getEmail());
                        claims.put("first_name", user.getFirstName());
                        claims.put("last_name", user.getLastName());
                        claims.put("token_created_at", String.valueOf(System.currentTimeMillis()));
                    });
                    logger.info("Added custom claims for CustomUserDetails principal.");
                } else if (principal.getPrincipal() instanceof OidcUser oidcUser) {
                    context.getClaims().claims(claims -> {
                        claims.put("email", oidcUser.getEmail());
                        claims.put("first_name", oidcUser.getGivenName());
                        claims.put("last_name", oidcUser.getFamilyName());
                        claims.put("provider", "google");
                        claims.put("token_created_at", String.valueOf(System.currentTimeMillis()));
                    });
                    logger.info("Added custom claims for OIDC user principal.");
                }
            } else if ("id_token".equals(tokenType)) {
                if (principal.getPrincipal() instanceof OidcUser oidcUser) {
                    context.getClaims().claims(claims -> {
                        claims.put("email", oidcUser.getEmail());
                        claims.put("given_name", oidcUser.getGivenName());
                        claims.put("family_name", oidcUser.getFamilyName());
                    });
                    logger.info("Customized ID Token claims for OIDC user.");
                }
            }

            logger.debug("Claims after customization: {}", context.getClaims().build());
        };
    }
}

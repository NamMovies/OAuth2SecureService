package antonBurshteyn.service;

import antonBurshteyn.dto.UserProfileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService implements ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);


    @Override
    public UserProfileDto getUserProfile(Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        logger.debug("Generating profile for user with email: {}", email);
        return new UserProfileDto(
                jwt.getClaimAsString("email"),
                jwt.getClaimAsString("first_name"),
                jwt.getClaimAsString("last_name"),
                jwt.getClaimAsString("provider"),
                jwt.getIssuedAt(),
                jwt.getExpiresAt()
        );
    }
}

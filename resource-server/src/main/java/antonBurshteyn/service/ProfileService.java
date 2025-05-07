package antonBurshteyn.service;

import antonBurshteyn.dto.UserProfileDto;
import org.springframework.security.oauth2.jwt.Jwt;

public interface ProfileService {

    UserProfileDto getUserProfile(Jwt jwt);
}

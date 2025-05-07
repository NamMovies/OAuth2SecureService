package antonBurshteyn.controller;

import antonBurshteyn.dto.UserProfileDto;
import antonBurshteyn.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private final ProfileService profileService;

    public ResourceController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Public page no need authorization";
    }

    @GetMapping("/secure")
    public ResponseEntity<UserProfileDto> getUserProfile(@AuthenticationPrincipal Jwt jwt) {
        logger.info("JWT claims received on 8081: " + jwt.getClaims());
        return ResponseEntity.ok(profileService.getUserProfile(jwt));
    }

    @GetMapping("/token")
    public Map<String, Object> getTokenInfo(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "token", jwt.getTokenValue(),
                "issuedAt", jwt.getIssuedAt(),
                "expiresAt", jwt.getExpiresAt(),
                "claims", jwt.getClaims()
        );
    }
}


package antonBurshteyn.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

public record UserProfileDto(
        String email,
        String firstName,
        String lastName,
        String provider,
        Instant issuedAt,
        Instant expiresAt
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1232627545531606148L;
}

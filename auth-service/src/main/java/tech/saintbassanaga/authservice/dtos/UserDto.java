package tech.saintbassanaga.authservice.dtos;

import tech.saintbassanaga.authservice.models.mapped.Biometric;
import tech.saintbassanaga.authservice.models.mapped.Civility;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.authservice.models.User}
 */
public record UserDto(UUID uuid, /*LocalDateTime creationDateTime, LocalDateTime lastModificationDateTime,*/
                      Civility civility, String credentialUsername, String credentialEmail, String credentialPhone,
                      Biometric biometric) implements Serializable {
}
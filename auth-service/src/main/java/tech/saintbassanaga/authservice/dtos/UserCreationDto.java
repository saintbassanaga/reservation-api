package tech.saintbassanaga.authservice.dtos;

import tech.saintbassanaga.authservice.models.mapped.Civility;

import java.io.Serializable;

/**
 * DTO for {@link tech.saintbassanaga.authservice.models.User}
 */
public record UserCreationDto(
        String credentialUsername, String credentialPassword, String credentialEmail,
        String credentialPhone) implements Serializable {
}
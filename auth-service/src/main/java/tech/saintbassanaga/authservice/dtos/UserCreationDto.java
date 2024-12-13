package tech.saintbassanaga.authservice.dtos;

import jakarta.validation.constraints.NotNull;
import tech.saintbassanaga.authservice.models.mapped.Civility;

import java.io.Serializable;

/**
 * DTO for {@link tech.saintbassanaga.authservice.models.User}
 */
public record UserCreationDto(
        @NotNull(message = "Civility can not be null") Civility civility,
        String credentialUsername, String credentialPassword, String credentialEmail,
        String credentialPhone) implements Serializable {
}
package tech.saintbassanaga.authservice.dtos;

import java.io.Serializable;

/**
 * DTO for {@link tech.saintbassanaga.authservice.models.mapped.Credential}
 */

public record LoginDto(String username, String password) implements Serializable {
}
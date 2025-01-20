package tech.saintbassanaga.authservice.dtos;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.authservice.models.User}
 */
public record AuthResponse(String accessToken, String refreshToken) implements Serializable {}
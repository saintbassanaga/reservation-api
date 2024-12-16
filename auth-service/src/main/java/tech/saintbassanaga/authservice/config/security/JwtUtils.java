package tech.saintbassanaga.authservice.config.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;


import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Configuration
public class JwtUtils {

    // Secret key for signing the JWT (HMAC)
    private final String SECRET_KEY = "Z3VkWmZvU2Q5QW9LRHVqZlZXaDFMSmh6cW93a0RqZXFTckNNRHlNZU1aNGRHTk9SZENubUZHVTBYVzBiRQ==";

    // Token validity duration
    private static final long TOKEN_VALIDITY_SECONDS = 24 * 60 * 60; // 24 hours

    /**
     * Configures a `JwtEncoder` to sign tokens.
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKeySpec));
    }

    /**
     * Configures a `JwtDecoder` to validate and parse tokens.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }

    /**
     * Generates a JWT token based on the authenticated user details.
     *
     * @param authentication the authentication object containing user information.
     * @return the generated JWT token.
     */
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Instant now = Instant.now();

        // Build the claims
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("your-application") // Replace with your application identifier
                .issuedAt(now)
                .expiresAt(now.plus(TOKEN_VALIDITY_SECONDS, ChronoUnit.SECONDS))
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities()) // Add roles or other custom claims
                .build();

        // Encode the token
        return jwtEncoder().encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }



    /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token the JWT token.
     * @return the username extracted from the token.
     */
    public String extractUsername(String token) {
        return decodeToken(token).getSubject();
    }

    /**
     * Checks if the token has expired.
     *
     * @param token the JWT token.
     * @return true if the token has expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        Instant expiration = decodeToken(token).getExpiresAt();
        assert expiration != null;
        return expiration.isBefore(Instant.now());
    }

    /**
     * Validates the given JWT token against the provided user details.
     *
     * @param token       the JWT token.
     * @param userDetails the user details to validate against.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Decodes and validates a JWT token.
     *
     * @param token the JWT token.
     * @return the decoded JWT object.
     */
    private Jwt decodeToken(String token) {
        return jwtDecoder().decode(token);
    }
}

package tech.saintbassanaga.authservice.config.security;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Configuration
public class JwtUtils {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    // Token validity durations
    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 24 * 60 * 60; // 24 hours
    private static final long REFRESH_TOKEN_VALIDITY_DAYS = 7; // 7 days

    /**
     * Constructor: Loads RSA public and private keys from PEM files during initialization.
     */
    public JwtUtils() {
        try {
            this.privateKey = loadPrivateKey("private_key.pem");
            this.publicKey = loadPublicKey("public_key.pem");
        } catch (Exception e) {
            throw new RuntimeException("Error loading RSA keys", e);
        }
    }

    /**
     * Load RSA Private Key from a PEM file.
     *
     * @param resourcePath Path to the PEM file containing the private key.
     * @return RSAPrivateKey instance.
     * @throws Exception If the file or key parsing fails.
     */
    private RSAPrivateKey loadPrivateKey(String resourcePath) throws Exception {
        String keyContent = loadKeyContent(resourcePath);
        keyContent = keyContent.replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", ""); // Remove header/footer and whitespaces
        byte[] keyBytes = Base64.getDecoder().decode(keyContent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    /**
     * Load RSA Public Key from a PEM file.
     *
     * @param resourcePath Path to the PEM file containing the public key.
     * @return RSAPublicKey instance.
     * @throws Exception If the file or key parsing fails.
     */
    private RSAPublicKey loadPublicKey(String resourcePath) throws Exception {
        String keyContent = loadKeyContent(resourcePath);
        keyContent = keyContent.replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", ""); // Remove header/footer and whitespaces
        byte[] keyBytes = Base64.getDecoder().decode(keyContent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * Utility method to load key content as a String from a resource file.
     *
     * @param resourcePath Path to the resource file containing the key.
     * @return Key content as a String.
     * @throws Exception If the file cannot be read.
     */
    private String loadKeyContent(String resourcePath) throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource file not found: " + resourcePath);
            }
            return new String(inputStream.readAllBytes());
        }
    }

    /**
     * Creates a JwtEncoder bean using the RSA keys.
     *
     * @return JwtEncoder for signing tokens.
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new com.nimbusds.jose.jwk.JWKSet(rsaKey));
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * Creates a JwtDecoder bean for verifying tokens using the public key.
     *
     * @return JwtDecoder for token validation.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    /**
     * Generates an access token for a user based on their authentication details.
     *
     * @param authentication The authentication object containing user details.
     * @return Signed JWT access token as a String.
     */
    public String generateAccessToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("your-application")
                .issuedAt(now)
                .expiresAt(now.plus(ACCESS_TOKEN_VALIDITY_SECONDS, ChronoUnit.SECONDS))
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .build();

        return jwtEncoder().encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    /**
     * Generates a refresh token for a user with a longer validity period.
     *
     * @param authentication The authentication object containing user details.
     * @return Signed JWT refresh token as a String.
     */
    public String generateRefreshToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("your-application")
                .issuedAt(now)
                .expiresAt(now.plus(REFRESH_TOKEN_VALIDITY_DAYS, ChronoUnit.DAYS))
                .subject(userDetails.getUsername())
                .claim("type", "refresh")
                .build();

        return jwtEncoder().encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    /**
     * Extracts the username (subject) from a given JWT token.
     *
     * @param token JWT token.
     * @return Username as a String.
     */
    public String extractUsername(String token) {
        return jwtDecoder().decode(token).getSubject();
    }

    /**
     * Checks if a token is expired based on its expiration time.
     *
     * @param token JWT token.
     * @return True if the token is expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        Instant expiration = jwtDecoder().decode(token).getExpiresAt();
        return expiration.isBefore(Instant.now());
    }

    /**
     * Validates the token by checking the username and expiration status.
     *
     * @param token JWT token to validate.
     * @param userDetails UserDetails object for comparison.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}

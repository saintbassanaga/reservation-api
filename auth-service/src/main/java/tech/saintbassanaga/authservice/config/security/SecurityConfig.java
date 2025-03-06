package tech.saintbassanaga.authservice.config.security;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtDecoder jwtDecoder, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtDecoder = jwtDecoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF as JWT is immune to CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Use stateless session management as we are using JWT
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                // Define authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/register").permitAll()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Handle exceptions for unauthorized and access denied scenarios
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((req, res, authException) -> {
                            res.setContentType("application/json");
                            res.sendError(HttpStatus.SC_UNAUTHORIZED, "Unauthorized");
                            res.encodeRedirectURL("/login");
                        })
                        .accessDeniedHandler((req, res, accessDeniedException) -> {
                            res.setContentType("application/json");
                            res.sendError(HttpStatus.SC_FORBIDDEN, "Access Denied");
                        })
                )
                // Configure the resource server to use JWT
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder)));

        return http.build();
    }
}

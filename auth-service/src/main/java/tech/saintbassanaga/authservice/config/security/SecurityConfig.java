package tech.saintbassanaga.authservice.config.security;

/*
 * Created by saintbassanaga
 * In the Project reservation-api at Fri - 12/13/24
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

   public final  JwtDecoder jwtDecoder;

   public final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtDecoder jwtDecoder, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtDecoder = jwtDecoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedPage("/access-denied")
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendError(403);
                            response.setContentType("application/json");
                            request.getSession().setMaxInactiveInterval(60 * 60);
                        })
                )
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .anyRequest().permitAll())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                )
                .passwordManagement(configurer -> configurer.changePasswordPage("/update-password"))
                .oauth2ResourceServer(
                        (oauth2) -> oauth2.jwt(
                                jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder)
                        )
                );
        return http.build();

    }


}

package tech.saintbassanaga.authservice.config.security;

/*
 * Created by saintbassanaga
 * In the Project reservation-api at Fri - 12/13/24
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.util.AntPathMatcher;

import java.security.interfaces.RSAPublicKey;

@Configuration
public class SecurityConfig {

   public final  JwtDecoder jwtDecoder;

    public SecurityConfig(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
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
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/**").hasAuthority("USER")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(1)
                        .expiredUrl("/login?expired=true")
                        .maxSessionsPreventsLogin(true)
                        .expiredSessionStrategy(SessionInformationExpiredEvent::getSessionInformation)
                )
                .oauth2ResourceServer(
                        (oauth2) -> oauth2.jwt(
                                jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder)
                        )
                )
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer
                        .ignoringRequestMatchers("/api/no-csrf/**"));
        return http.build();

    }

}

package tech.saintbassanaga.authservice.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.saintbassanaga.authservice.services.UserService;
import tech.saintbassanaga.authservice.services.impls.UserServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT Authentication Filter
 * Created by saintbassanaga
 * In the Project reservation-api at Sat - 12/14/24
 */

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserService userDetailsService;


    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain)
            throws ServletException, IOException {

        // Extract Authorization header
        final String authHeader = request.getHeader("Authorization");

        // Check if the header is missing or does not start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response); // Pass the request to the next filter
            return;
        }

        // Extract the JWT token from the Authorization header
        final String token = authHeader.substring(7);

        try {
            // Decode and validate the token
            Jwt jwt = jwtUtils.jwtDecoder().decode(token);

            // Extract username (subject) from JWT
            String username = jwtUtils.extractUsername(token);

            // Proceed only if username is not null and no user is authenticated yet
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details from UserDetailsService

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Extract roles from JWT claims and convert to authorities
                List<SimpleGrantedAuthority> authorities = ((List<?>) jwt.getClaim("roles")).stream()
                        .map(role -> new SimpleGrantedAuthority(role.toString()))
                        .collect(Collectors.toList());

                // Create an authentication token and set it in the security context
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException e) {
            // If token is invalid, log the exception and proceed without authentication
            System.err.println("Invalid JWT token: " + e.getMessage());
        }

        // Continue the filter chain
        chain.doFilter(request, response);
    }

}

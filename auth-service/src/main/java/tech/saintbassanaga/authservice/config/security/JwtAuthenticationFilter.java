package tech.saintbassanaga.authservice.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Created by saintbassanaga {stpaul}
 * In the Project reservation-api at Sat - 12/14/24
 */
public class JwtAuthenticationFilter {

    public void doInternalFilter(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        System.out.println("Filtering request");
        chain.doFilter(request, response);
    }
}

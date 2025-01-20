package tech.saintbassanaga.authservice.services.impls;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tech.saintbassanaga.authservice.config.security.JwtUtils;
import tech.saintbassanaga.authservice.dtos.AuthResponse;
import tech.saintbassanaga.authservice.dtos.DtoMappers;
import tech.saintbassanaga.authservice.dtos.LoginDto;
import tech.saintbassanaga.authservice.dtos.UserCreationDto;
import tech.saintbassanaga.authservice.models.User;
import tech.saintbassanaga.authservice.repository.UserRepository;
import tech.saintbassanaga.authservice.services.UserService;

/**
 * Created by saintbassanaga
 * In the Project reservation-api at Fri - 12/13/24
 */
@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DtoMappers dtoMappers;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, DtoMappers dtoMappers, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.dtoMappers = dtoMappers;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Handles user login.
     *
     * @param request LoginRequest containing username and password.
     * @return AuthResponse with generated tokens.
     */
    public AuthResponse login(LoginDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        String accessToken = jwtUtils.generateAccessToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);

        return new AuthResponse(accessToken, refreshToken);
    }
    /**
     * Handles user registration.
     *
     * @param request RegisterRequest containing user credentials.
     * @return Success message on successful registration.
     */
    public String register(UserCreationDto request) {
        if (userRepository.existsByCredential_Username(request.credentialUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        userRepository.save(dtoMappers.userCreation(request));
        return "User registered successfully. Please log in.";
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may be case-sensitive or case-insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested.
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByCredential_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}

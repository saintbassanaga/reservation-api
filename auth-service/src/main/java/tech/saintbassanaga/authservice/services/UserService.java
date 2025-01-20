package tech.saintbassanaga.authservice.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import tech.saintbassanaga.authservice.dtos.AuthResponse;
import tech.saintbassanaga.authservice.dtos.LoginDto;
import tech.saintbassanaga.authservice.dtos.UserCreationDto;

/**
 * Created by saintbassanaga
 * In the Project reservation-api at Fri - 12/13/24
 */
@Service
public interface UserService extends UserDetailsService {
    public AuthResponse login(LoginDto request);
    public String register(UserCreationDto request);
}

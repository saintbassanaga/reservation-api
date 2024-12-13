package tech.saintbassanaga.authservice.services.impls;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.saintbassanaga.authservice.repository.UserRepository;

/**
 * Created by saintbassanaga
 * In the Project reservation-api at Fri - 12/13/24
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

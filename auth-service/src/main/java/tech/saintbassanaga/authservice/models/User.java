package tech.saintbassanaga.authservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.saintbassanaga.authservice.models.mapped.Biometric;
import tech.saintbassanaga.authservice.models.mapped.Civility;
import tech.saintbassanaga.authservice.models.mapped.Credential;
import tech.saintbassanaga.authservice.models.mapped.Role;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
@Table(name = "users" , indexes = {
        @Index(name = "user_uuid_username_index", columnList = "uuid ,username")
})
public class User extends AuditingEntity implements UserDetails {
    private Civility civility;
    private Credential credential;
    private Biometric biometric;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return a single GrantedAuthority object
        return Collections.singleton(() -> "ROLE_" + role.toString());
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return credential.getPassword();
    }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return credential.getUsername();
    }
}
package tech.saintbassanaga.authservice.models.mapped;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Credential {
    private String username;
    private String password;
    private String email;
    private String phone;
}
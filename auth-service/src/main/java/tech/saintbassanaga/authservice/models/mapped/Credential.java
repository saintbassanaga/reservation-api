package tech.saintbassanaga.authservice.models.mapped;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Credential {
    private String username;
    private String password;
    private String email;
    private String phone;

}
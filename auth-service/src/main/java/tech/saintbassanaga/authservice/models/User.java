package tech.saintbassanaga.authservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tech.saintbassanaga.authservice.models.mapped.Civility;
import tech.saintbassanaga.authservice.models.mapped.Credential;

@Getter
@Setter
@Entity
@Table(name = "users" , indexes = {
        @Index(name = "user_uuid_username_index", columnList = "uuid ,username")
})
public class User extends AuditingEntity {
    private Civility civility;
    private Credential credential;
}
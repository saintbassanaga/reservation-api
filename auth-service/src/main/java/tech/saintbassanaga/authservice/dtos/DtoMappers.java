package tech.saintbassanaga.authservice.dtos;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tech.saintbassanaga.authservice.models.User;
import tech.saintbassanaga.authservice.models.mapped.Civility;
import tech.saintbassanaga.authservice.models.mapped.Credential;

/**
 * Created by saintbassanaga {saintbassanaga}
 * In the Project reservation-api at Tue - 12/17/24
 */

@Component
public class DtoMappers {
    private final PasswordEncoder passwordEncoder;

    public DtoMappers(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto fromUserToUserDto(User user) {
        return new UserDto(
                user.getUuid(),
/*                user.getCreationDateTime(),
                user.getLastModificationDateTime(),*/
                user.getCivility(),
                user.getCredential().getUsername(),
                user.getCredential().getEmail(),
                user.getCredential().getPhone(),
                user.getBiometric()
        );
    }

    public User userCreation(UserCreationDto userCreationDto) {

        User user = new User();
        user.setCredential(new Credential(
                userCreationDto.credentialUsername(),
                passwordEncoder.encode(userCreationDto.credentialPassword()),
                userCreationDto.credentialEmail(),
                userCreationDto.credentialPhone()
        ));
        return user;
    }

    public static Civility fromCivilityDtoToClass(CivilityDto civilityDto){
        Civility civility = new Civility();
        civility.setName(civilityDto.name());
        civility.setSurname(civilityDto.surname());
        civility.setCardNumber(civilityDto.cardNumber());
        return civility;
    }
}

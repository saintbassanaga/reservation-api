package tech.saintbassanaga.authservice.dtos;

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
    public UserDto fromUserToUserDto(User user) {
        return new UserDto(
                user.getUuid(),
                user.getCreationDateTime(),
                user.getLastModificationDateTime(),
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
                userCreationDto.credentialEmail(),
                userCreationDto.credentialPhone(),
                userCreationDto.credentialUsername(),
                userCreationDto.credentialPassword()
        ));
        return user;
    }

    public static Civility fromCivilityDtoToClass(CivilityDto civilityDto){
        Civility civility = new Civility();
        civility.setName(civilityDto.name());
        civility.setSurname(civilityDto.surname());
        civility.setBornDate(civilityDto.bornDate());
        civility.setCardNumber(civilityDto.cardNumber());
        return civility;
    }
}

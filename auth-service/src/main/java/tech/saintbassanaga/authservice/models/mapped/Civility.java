package tech.saintbassanaga.authservice.models.mapped;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Embeddable
public class Civility {
    private String name;
    private String surname;
    private LocalDate bornDate;
    private String cardNumber;
}
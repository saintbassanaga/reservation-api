package tech.saintbassanaga.authservice.dtos;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link tech.saintbassanaga.authservice.models.mapped.Civility}
 */
public record CivilityDto(String name, String surname, LocalDate bornDate, String cardNumber) implements Serializable {
  }
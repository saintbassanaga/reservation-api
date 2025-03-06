package tech.saintbassanaga.authservice.dtos;

import java.io.Serializable;
import java.time.YearMonth;

/**
 * DTO for {@link tech.saintbassanaga.authservice.models.mapped.Civility}
 */
public record CivilityDto(String name, String surname, YearMonth bornDate, String cardNumber) implements Serializable {
  }
package tech.saintbassanaga.flightservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class AirportInfo {
    private String iataCode;
    private String terminal;

    @Column(name = "departure_at")
    private String at;

    // Getters and Setters
}

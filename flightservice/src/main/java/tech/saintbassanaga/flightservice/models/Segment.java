package tech.saintbassanaga.flightservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "segments")
public class Segment extends AbstractEntity {
    private String carrierCode;
    private String number;
    private String duration;
    private int numberOfStops;
    private boolean blacklistedInEU;

    @ManyToOne
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    private AirportInfo departure;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "at", column = @Column(name = "arrival_at")),
            @AttributeOverride(name = "iataCode", column = @Column(name = "iataCode_arrival")),
            @AttributeOverride(name = "terminal", column = @Column(name = "terminal_arrival"))
    })
    private AirportInfo arrival;


    // Getters and Setters
}
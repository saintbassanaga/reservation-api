package tech.saintbassanaga.flightservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "itineraries")
public class Itinerary extends AbstractEntity {
    private String duration;

    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Segment> segments ;

    @ManyToOne
    @JoinColumn(name = "flight_offer_id")
    private FlightOffer flightOffer;

    // Getters and Setters
}

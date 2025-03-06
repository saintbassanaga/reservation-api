package tech.saintbassanaga.flightservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "traveler_pricings")
public class TravelerPricing extends AbstractEntity {
    private String travelerId;
    private String fareOption;
    private String travelerType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Price price;

    @OneToMany(mappedBy = "travelerPricing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FareDetailsBySegment> fareDetailsBySegment = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "flight_offer_id")
    private FlightOffer flightOffer;

    // Getters and Setters
}
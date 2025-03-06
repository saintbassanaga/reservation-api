package tech.saintbassanaga.flightservice.models;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "flight_offers")
public class FlightOffer extends AbstractEntity {
    private String type;
    private String source;
    private boolean instantTicketingRequired;
    private boolean nonHomogeneous;
    private boolean oneWay;
    private String lastTicketingDate;
    private int numberOfBookableSeats;

    @OneToMany(mappedBy = "flightOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Itinerary> itineraries = new ArrayList<>();

    @OneToOne(mappedBy = "flightOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Price price;

    @OneToMany(mappedBy = "flightOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelerPricing> travelerPricings = new ArrayList<>();

    // Getters and Setters
}
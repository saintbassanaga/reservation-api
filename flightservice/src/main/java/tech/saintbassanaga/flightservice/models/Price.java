package tech.saintbassanaga.flightservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "prices")
public class Price extends AbstractEntity {
    private String currency;
    private String total;
    private String base;
    private String grandTotal;

    @OneToMany(mappedBy = "price", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fee> fees = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "flight_offer_id")
    private FlightOffer flightOffer;

    // Getters and Setters
}
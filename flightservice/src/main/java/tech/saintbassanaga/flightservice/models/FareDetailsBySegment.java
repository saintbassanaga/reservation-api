package tech.saintbassanaga.flightservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fare_details_by_segment")
public class FareDetailsBySegment extends AbstractEntity {
    private String segmentId;
    private String cabin;
    private String fareBasis;
    private String travelClass;

    @Embedded
    private BaggageInfo includedCheckedBags;

    @ManyToOne
    @JoinColumn(name = "traveler_pricing_id")
    private TravelerPricing travelerPricing;

    // Getters and Setters
}
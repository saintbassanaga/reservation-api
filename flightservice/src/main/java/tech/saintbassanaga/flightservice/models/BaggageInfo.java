package tech.saintbassanaga.flightservice.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class BaggageInfo {
    private int weight;
    private String weightUnit;

    // Getters and Setters
}
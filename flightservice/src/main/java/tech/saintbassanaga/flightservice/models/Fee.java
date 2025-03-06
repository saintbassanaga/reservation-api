package tech.saintbassanaga.flightservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fees")
public class Fee extends AbstractEntity {
    private String amount;
    private String type;

    @ManyToOne
    @JoinColumn(name = "price_id")
    private Price price;

    // Getters and Setters
}
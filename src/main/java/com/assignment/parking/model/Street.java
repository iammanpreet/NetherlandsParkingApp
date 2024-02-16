package com.assignment.parking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Street")
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "street_id")
    private Long streetId;

    @Column(name="street_name",unique = true)
    private String name;
    @Column(name="price_per_min")
    private int pricePerMinute;

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(int pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }
}

package com.example.demo.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("CASH")
@Data
@EqualsAndHashCode(callSuper = true)
public class Cash extends Payment {
    private float cashTendered;

    @Override
    public String getType() {
        return "CASH";
    }
}
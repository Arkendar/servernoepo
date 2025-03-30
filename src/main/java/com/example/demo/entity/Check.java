package com.example.demo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("CHECK")
@Data
@EqualsAndHashCode(callSuper = true)
public class Check extends Payment {
    private String name;
    private String bankID;

    @Override
    public String getType() {
        return "CHECK";
    }
}
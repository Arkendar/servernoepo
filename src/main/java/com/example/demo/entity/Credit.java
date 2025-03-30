package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("CREDIT")
@Data
@EqualsAndHashCode(callSuper = true)
public class Credit extends Payment {
    private String number;
    private String cardType;
    private LocalDateTime expDate;

    @Override
    public String getType() {
        return "CREDIT";
    }
}
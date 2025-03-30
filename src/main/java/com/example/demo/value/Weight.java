package com.example.demo.value;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Embeddable
public class Weight {
    @Column(name = "shipping_weight_value")
    private BigDecimal value;
}
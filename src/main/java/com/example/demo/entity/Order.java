package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.value.OrderDetail;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime date;
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ElementCollection
    @CollectionTable(name = "order_details", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderDetail> details;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;
}
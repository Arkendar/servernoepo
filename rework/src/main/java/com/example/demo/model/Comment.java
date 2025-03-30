package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String author;
    private String content;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate;
    private String status = "ACTIVE"; // ACTIVE, DELETED
}
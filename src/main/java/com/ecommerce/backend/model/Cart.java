package com.ecommerce.backend.model;
import lombok.*;
import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
}
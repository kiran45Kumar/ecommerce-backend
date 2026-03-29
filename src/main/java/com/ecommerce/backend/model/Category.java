package com.ecommerce.backend.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
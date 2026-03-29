package com.ecommerce.backend.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import com.ecommerce.backend.model.Category;

@Entity
@Data

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
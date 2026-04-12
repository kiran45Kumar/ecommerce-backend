package com.ecommerce.backend.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private double totalAmount;
    private Date createdAt = new Date();
}
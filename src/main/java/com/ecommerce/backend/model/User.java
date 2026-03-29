package com.ecommerce.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import com.ecommerce.backend.model.Role;
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // USER / ADMIN
}
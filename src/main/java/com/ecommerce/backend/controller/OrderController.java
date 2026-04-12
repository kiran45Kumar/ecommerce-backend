package com.ecommerce.backend.controller;

import com.ecommerce.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.ecommerce.backend.dto.ApiResponse;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(ApiResponse.success("Order placed successfully", orderService.placeOrder(email)));
    }
    @GetMapping
    public ResponseEntity<?> getOrders(Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orderService.getUserOrders(email)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(ApiResponse.success("Order details retrieved successfully", orderService.getOrderItems(orderId)));
    }
}   
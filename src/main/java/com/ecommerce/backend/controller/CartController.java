package com.ecommerce.backend.controller;
import com.ecommerce.backend.model.*;
import com.ecommerce.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.ecommerce.backend.dto.ApiResponse;


@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor

public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Cart>> addToCart(Authentication authentication, @RequestParam Long productId, @RequestParam int quantity) {
        String email = authentication.getName();
        Cart cart = cartService.addToCart(email, productId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Product added to cart successfully", cart));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Cart>> getCart(Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(ApiResponse.success("Cart retrieved successfully", cartService.getCart(email)));
    }
}   
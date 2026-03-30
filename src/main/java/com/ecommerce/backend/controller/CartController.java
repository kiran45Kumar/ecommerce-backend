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

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor

public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(Authentication authentication, @RequestParam Long productId, @RequestParam int quantity) {
        String email = authentication.getName();
        Cart cart = cartService.addToCart(email, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<?> getCart(Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(cartService.getCart(email));
    }
}   
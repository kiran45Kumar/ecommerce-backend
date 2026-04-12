package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.backend.dto.LoginRequest;
import com.ecommerce.backend.dto.LoginResponse;
import com.ecommerce.backend.security.*;
import com.ecommerce.backend.dto.ApiResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
    try {
        User savedUser = userService.register(user);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", savedUser));
    } catch (RuntimeException ex) {
        // Return 400 Bad Request with message
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ex.getMessage(), null));
    }

    }

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
        User user = userService.login(request.getEmail(), request.getPassword());

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name() 
        );

        return ResponseEntity.ok(ApiResponse.success("Login successful", new LoginResponse(token)));

    } catch (RuntimeException ex) {
        return ResponseEntity.status(401).body(ApiResponse.error(ex.getMessage(), null));
    }
}

@PutMapping("/{id}")
public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
    try {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", user));
    } catch (RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ex.getMessage(), null));
    }

}
}

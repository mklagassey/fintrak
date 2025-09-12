package com.fintrak.userservice.controller;

import com.fintrak.userservice.dto.RegistrationRequest;
import com.fintrak.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fintrak.userservice.dto.LoginRequest;
import com.fintrak.userservice.dto.LoginResponse;
import com.fintrak.userservice.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
        userService.registerUser(request);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@AuthenticationPrincipal User user) {
        // The 'user' object is automatically populated by Spring Security
        // based on the validated JWT.
        return ResponseEntity.ok(user);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("User Service is up and running!");
    }
}
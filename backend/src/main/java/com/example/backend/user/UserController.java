package com.example.backend.user;

import com.example.backend.config.FirestoreService;
import com.example.backend.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class UserController {
    @Autowired
    FirestoreService firestoreService;
    @Autowired
    SecurityUtils securityUtils;

    @PostMapping("/api/register")
    public ResponseEntity<String> registerUser(@RequestBody User newUser) {
        try {
            User existingUser = firestoreService.getUserByEmail(newUser.getEmail());

            if (existingUser != null) {
                return ResponseEntity.badRequest().body("Email already registered");
            }

            if (firestoreService.isUsernameTaken(newUser.getUserName())) {
                return ResponseEntity.badRequest().body("Username already taken");
            }

            newUser.setUserId(UUID.randomUUID().toString());
            newUser.setPassword(securityUtils.encrpytPassword(newUser.getPassword()));
            firestoreService.addUser(newUser);

            return ResponseEntity.ok("User registered successfully");
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user");
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        try {
            User existingUser = firestoreService.getUserByEmail(user.getEmail());

            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid");
            }

            if (securityUtils.isPasswordValid(user.getPassword(), existingUser.getPassword())) {
                return ResponseEntity.ok("User logged in successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login");
        }
    }
}

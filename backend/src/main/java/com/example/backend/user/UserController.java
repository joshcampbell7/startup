package com.example.backend.user;

import com.example.backend.config.FirestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class UserController {
    @Autowired
    FirestoreService firestoreService;

    @GetMapping("/hello")
    public String hello() throws ExecutionException, InterruptedException {
        User test = firestoreService.getUserByEmail("joshuasdssa@test.com");
        if (test != null) {
            System.out.println("email in database");
        } else {
            System.out.println("email not in database");
        }
        firestoreService.addUser();
        List<User> users = firestoreService.getAllUsers();
        StringBuilder response = new StringBuilder("Hello, World! Users: ");
        for (User user : users) {
            response.append(user.getFirstName().toString()).append(", ");
        }

        // Return a response containing user data
        return response.toString();
    }
}

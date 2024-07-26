package com.example.backend.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String userId;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;

    public User(String email, String userName, String firstName, String lastName, String password) {
        this.userId = UUID.randomUUID().toString();
        this.email = email;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

}

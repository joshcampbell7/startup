package com.example.backend.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

}

package com.example.LibraryManagement.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String password;

    private Set<RoleName> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String password, Set<RoleName> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

}
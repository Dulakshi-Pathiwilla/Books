package com.example.LibraryManagement.Payload.Request;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String username;
    private String password;
    private Set<String> roles; // e.g. ["admin"] or ["user"]

}

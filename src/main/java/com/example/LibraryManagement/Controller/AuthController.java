package com.example.LibraryManagement.Controller;

import com.example.LibraryManagement.Entity.RoleName;
import com.example.LibraryManagement.Entity.User;
import com.example.LibraryManagement.Payload.Request.LoginRequest;
import com.example.LibraryManagement.Payload.Request.SignUpRequest;
import com.example.LibraryManagement.Payload.Response.JwtResponse;
import com.example.LibraryManagement.Repository.UserRepository;
import com.example.LibraryManagement.Security.Jwt.JwtUtils;
import com.example.LibraryManagement.Security.Services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        // Parse roles from request
        Set<RoleName> roles = new HashSet<>();

        if (signUpRequest.getRoles() == null || signUpRequest.getRoles().isEmpty()) {
            roles.add(RoleName.ROLE_USER); // default
        } else {
            signUpRequest.getRoles().forEach(r -> {
                if ("admin".equalsIgnoreCase(r)) {
                    roles.add(RoleName.ROLE_ADMIN);
                } else { // "user" or anything else
                    roles.add(RoleName.ROLE_USER);
                }
            });
        }

        // Create new user
        User user = new User(
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                roles);

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

}

package com.shiv.PatelPOS.service;
import com.shiv.PatelPOS.dto.AuthResponse;
import com.shiv.PatelPOS.dto.LoginRequest;
import com.shiv.PatelPOS.dto.RegisterRequest;
import com.shiv.PatelPOS.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        Set<String> allowedRoles = Set.of("ROLE_STAFF", "ROLE_MANAGER");

        Set<String> rolesToSave;
        if(request.getRoles() == null || request.getRoles().isEmpty()) {
            rolesToSave = Set.of("ROLE_STAFF");
        }
        else {
            rolesToSave = request.getRoles().stream()
                    .map(r -> r.trim().toUpperCase().startsWith("ROLE_") ? r.trim().toUpperCase()
                            : "ROLE_" + r.trim().toUpperCase())
                    .peek(r -> {
                        if(!allowedRoles.contains(r)) {
                            throw new IllegalArgumentException("Invalid role: " + r);
                        }
                    })
                    .collect(Collectors.toCollection(java.util.LinkedHashSet::new));
        }

        User user =  new User(request.getUsername()) ;
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(rolesToSave);


        userService.saveUser(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userService.findByUserName(request.getUsername());

        if(user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}

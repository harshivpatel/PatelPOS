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

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private com.shiv.PatelPOS.jwt.JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singletonList("USER"));

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

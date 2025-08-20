package com.shiv.PatelPOS.controller;

import com.shiv.PatelPOS.dto.AuthResponse;
import com.shiv.PatelPOS.dto.LoginRequest;
import com.shiv.PatelPOS.dto.RegisterRequest;
import com.shiv.PatelPOS.service.AuthService;
import com.shiv.PatelPOS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse register = authService.register(request);
        return ResponseEntity.ok(register);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse login = authService.login(request);
        return ResponseEntity.ok(login);
    }

}

package com.shiv.PatelPOS.controller;

import com.shiv.PatelPOS.entity.User;
import com.shiv.PatelPOS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }
    @DeleteMapping("{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "User Deleted successfully";
    }
    @PutMapping("{id}/password")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody String newPassword) {
        User userInDb = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userInDb.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(userInDb);

        return ResponseEntity.ok("Password updated successfully");
    }

}

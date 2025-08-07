package com.shiv.PatelPOS.service;

import com.shiv.PatelPOS.entity.User;
import com.shiv.PatelPOS.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }
        catch (Exception e) {
            log.error("Error occurred for {}", user.getUsername(), e);
            return false;
        }

    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }
}

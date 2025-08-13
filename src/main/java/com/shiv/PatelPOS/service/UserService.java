package com.shiv.PatelPOS.service;

import com.shiv.PatelPOS.entity.User;
import com.shiv.PatelPOS.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public User saveUser(User user) {
            return userRepository.save(user);
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

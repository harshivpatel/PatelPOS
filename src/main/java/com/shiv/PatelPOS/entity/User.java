package com.shiv.PatelPOS.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}

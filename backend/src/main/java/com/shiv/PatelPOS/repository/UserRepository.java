package com.shiv.PatelPOS.repository;

import com.shiv.PatelPOS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String userName);

    void deleteByUsername(String username);
}

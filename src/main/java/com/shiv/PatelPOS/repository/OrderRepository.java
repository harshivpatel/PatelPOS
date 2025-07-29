package com.shiv.PatelPOS.repository;

import com.shiv.PatelPOS.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

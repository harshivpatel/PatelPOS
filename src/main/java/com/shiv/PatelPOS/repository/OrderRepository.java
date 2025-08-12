package com.shiv.PatelPOS.repository;

import com.shiv.PatelPOS.dto.OrderResponseDTO;
import com.shiv.PatelPOS.entity.Order;
import com.shiv.PatelPOS.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

package com.shiv.PatelPOS.repository;

import com.shiv.PatelPOS.dto.OrderResponseDTO;
import com.shiv.PatelPOS.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE " +
            "CAST(o.orderDate AS string) LIKE %:keyword% OR " +
            "CAST(o.totalAmount AS string) LIKE %:keyword%")
    List<Order> searchOrders(@Param("keyword") String keyword);


}

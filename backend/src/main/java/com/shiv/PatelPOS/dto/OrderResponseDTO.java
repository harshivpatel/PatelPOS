package com.shiv.PatelPOS.dto;

import com.shiv.PatelPOS.entity.Order;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private List<OrderItemsResponseDTO> items;
    private Order.PaymentMode paymentMode;
}

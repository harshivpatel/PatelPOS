package com.shiv.PatelPOS.dto;

import com.shiv.PatelPOS.entity.Order;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private Date orderDate;
    private double totalAmount;
    private List<OrderItemsResponseDTO> items;
}

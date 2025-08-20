package com.shiv.PatelPOS.dto;

import lombok.Data;

@Data
public class OrderItemsResponseDTO {
    private String productName;
    private int quantity;
    private double subtotal;
}

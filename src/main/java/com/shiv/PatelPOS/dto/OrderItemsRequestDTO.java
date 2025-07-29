package com.shiv.PatelPOS.dto;

import lombok.Data;

@Data
public class OrderItemsRequestDTO {
    private Long productId;
    private int quantity;
}

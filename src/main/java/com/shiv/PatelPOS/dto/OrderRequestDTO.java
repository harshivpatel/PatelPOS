package com.shiv.PatelPOS.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private List<OrderItemsRequestDTO> items;
}

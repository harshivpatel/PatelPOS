package com.shiv.PatelPOS.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long productId;
    private String name;
    private Double price;
    private Integer stockQuantity;
    private String category;
}

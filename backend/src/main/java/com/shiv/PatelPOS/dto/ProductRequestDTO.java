package com.shiv.PatelPOS.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDTO {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be positive")
    private Double price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Quantity must be 0 or more")
    private Integer stockQuantity;

    @NotBlank(message = "Stock category is required")
    private String category;
}

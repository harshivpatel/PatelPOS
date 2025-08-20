package com.shiv.PatelPOS.mapper;

import com.shiv.PatelPOS.dto.ProductRequestDTO;
import com.shiv.PatelPOS.dto.ProductResponseDTO;
import com.shiv.PatelPOS.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(dto.getCategory());

        return product;
    }

    public static ProductResponseDTO toDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        return dto;
    }
}

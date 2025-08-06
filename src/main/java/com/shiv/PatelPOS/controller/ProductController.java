package com.shiv.PatelPOS.controller;

import com.shiv.PatelPOS.dto.ProductRequestDTO;
import com.shiv.PatelPOS.dto.ProductResponseDTO;
import com.shiv.PatelPOS.entity.Product;
import com.shiv.PatelPOS.mapper.ProductMapper;
import com.shiv.PatelPOS.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> saveProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        Product product = ProductMapper.toEntity(productRequestDTO);
        Product savedProduct = productService.saveProduct(product);
        ProductResponseDTO productResponseDTO = ProductMapper.toDTO(savedProduct);
        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping
    public List<Product> getProductsList () {
        return productService.fetchProducts();
    }
    @PutMapping("{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable("id") Long id) {
        return productService.updateProduct(product, id);
    }
    @DeleteMapping("{id}")
    public String deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "Deleted Successfully";
    }
}

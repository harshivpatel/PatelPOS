package com.shiv.PatelPOS.service;

import com.shiv.PatelPOS.entity.Product;
import com.shiv.PatelPOS.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    public final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /// save
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    /// get
    public List<Product> fetchProducts() {
        return productRepository.findAll();
    }
    /// update
    public Product updateProduct(Product updatedProduct, Long productId) {

        // this is just a wrapper returned by the repo, doesn't give direct access to perform operations
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isPresent()) {
            // to perform operations on wrapper we use .get(), existingProduct is the one in db we want to update
            Product existingProduct = optionalProduct.get();

            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setStockQuantity(updatedProduct.getStockQuantity());

            return productRepository.save(existingProduct);
        }
        else {
            throw new EntityNotFoundException("Product not found with id: " + productId);
        }
    }

    /// delete
    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }

    /// search with keyword
    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }

    /// sorting based on field
    public List<Product> findProductByField(String field) {
        return productRepository.findAll(Sort.by(field));
    }
}

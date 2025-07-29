package com.shiv.PatelPOS.repository;

import com.shiv.PatelPOS.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

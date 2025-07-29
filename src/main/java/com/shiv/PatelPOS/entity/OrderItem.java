package com.shiv.PatelPOS.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // If an order has 3 items, all 3 OrderItem objects will have the same Order reference.
    @JoinColumn(name = "order_id") // foreign key
    Order order;

    @ManyToOne // Many OrderItems can refer to the same Product.
    @JoinColumn(name = "product_id")
    Product product;

    private int quantity;

    private double subTotal;

    private double priceAtPurchase;
}

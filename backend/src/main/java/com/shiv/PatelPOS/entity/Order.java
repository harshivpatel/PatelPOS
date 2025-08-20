package com.shiv.PatelPOS.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private LocalDateTime orderDate;
    private double totalAmount;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public enum PaymentMode {
        CASH, CARD
    }
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
}

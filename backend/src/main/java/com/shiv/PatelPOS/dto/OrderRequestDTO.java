package com.shiv.PatelPOS.dto;

import com.shiv.PatelPOS.entity.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    private List<OrderItemsRequestDTO> items;
    private Order.PaymentMode paymentMode;
}

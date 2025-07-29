package com.shiv.PatelPOS.mapper;

import com.shiv.PatelPOS.dto.OrderItemsResponseDTO;
import com.shiv.PatelPOS.dto.OrderResponseDTO;
import com.shiv.PatelPOS.entity.Order;
import com.shiv.PatelPOS.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static OrderResponseDTO mapOrderResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
            dto.setOrderId(order.getOrderId());
            dto.setOrderDate(order.getOrderDate());
            dto.setTotalAmount(order.getTotalAmount());

        List<OrderItemsResponseDTO> itemsDTOs = new ArrayList<>();

        for(OrderItem item: order.getOrderItems()) {
            OrderItemsResponseDTO itemDTO = new OrderItemsResponseDTO();
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setSubtotal(item.getSubTotal());

            itemsDTOs.add(itemDTO);
        }
        dto.setItems(itemsDTOs);
        return dto;
    }
}

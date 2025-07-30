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
/**
 * Converts an Order entity to an OrderResponseDTO for API responses.
 * This method avoids exposing the full entity structure and sensitive data,
 * and formats the response in a cleaner, more controlled way using DTOs.
 *
 * Steps:
 * 1. Create an OrderResponseDTO and populate it with basic order details.
 * 2. Initialize an empty list of OrderItemsResponseDTO.
 * 3. Loop through each OrderItem in the given Order:
 *    - For each item, create a new OrderItemsResponseDTO.
 *    - Extract required fields like product name, quantity, and subtotal.
 *    - Add the mapped item DTO to the list.
 * 4. Set the list of item DTOs into the main OrderResponseDTO.
 * 5. Return the fully constructed OrderResponseDTO.
 */

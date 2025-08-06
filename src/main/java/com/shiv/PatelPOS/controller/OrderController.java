package com.shiv.PatelPOS.controller;

import com.shiv.PatelPOS.dto.OrderRequestDTO;
import com.shiv.PatelPOS.dto.OrderResponseDTO;
import com.shiv.PatelPOS.entity.Order;
import com.shiv.PatelPOS.entity.Product;
import com.shiv.PatelPOS.mapper.OrderMapper;
import com.shiv.PatelPOS.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping
    public ResponseEntity<OrderResponseDTO> saveOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        Order savedOrder = orderService.saveOrder(orderRequestDTO);
        OrderResponseDTO responseDTO = OrderMapper.mapOrderResponseDTO(savedOrder);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponseDTO> dtoList = orders.stream()
                .map(OrderMapper::mapOrderResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @RequestBody @Valid OrderRequestDTO orderRequestDTO,
            @PathVariable("id") Long id) {
        Order updatedOrder = orderService.updateOrderById(orderRequestDTO, id);
        OrderResponseDTO orderResponseDTO = OrderMapper.mapOrderResponseDTO(updatedOrder);
        return ResponseEntity.ok(orderResponseDTO);
    }
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrderById(id);
        return "Order Deleted Successfully";
    }
    @GetMapping("/search")
    public ResponseEntity<List<OrderResponseDTO>> searchOrders(@RequestParam String keyword) {
        List<Order> orders = orderService.searchOrders(keyword);
        // Convert the list of Order entities to OrderResponseDTOs using the mapper
        // and return the DTO list as the response body
        List<OrderResponseDTO> orderResponseDTO = orders.stream()
                .map(OrderMapper::mapOrderResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderResponseDTO);
    }
}

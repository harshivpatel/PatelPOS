package com.shiv.PatelPOS.controller;

import com.shiv.PatelPOS.dto.OrderRequestDTO;
import com.shiv.PatelPOS.entity.Order;
import com.shiv.PatelPOS.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping
    public Order saveOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.saveOrder(orderRequestDTO);
    }
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    @PutMapping("/{id}")
    public Order updateOrder(@RequestBody Order order, @PathVariable("id") Long id) {
        return orderService.updateOrderById(order, id);
    }
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrderById(id);
        return "Order Deleted Successfully";
    }
}

package com.shiv.PatelPOS.service;

import com.shiv.PatelPOS.dto.OrderItemsRequestDTO;
import com.shiv.PatelPOS.dto.OrderRequestDTO;
import com.shiv.PatelPOS.entity.Order;
import com.shiv.PatelPOS.entity.OrderItem;
import com.shiv.PatelPOS.entity.Product;
import com.shiv.PatelPOS.repository.OrderRepository;
import com.shiv.PatelPOS.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }
    /// save
    public Order saveOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setOrderDate(new Date());
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for(OrderItemsRequestDTO itemDTO: orderRequestDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() ->
                            new EntityNotFoundException
                                    ("Product not found with id " + itemDTO.getProductId()));

            /// stock availability
            if(product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for the product " + product.getName());
            }

            /// decrease product stock
            product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());

            /// create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItem.setSubTotal(product.getPrice() * itemDTO.getQuantity());
            orderItem.setOrder(order);

            ///  add to order
            orderItems.add(orderItem);

            /// total running price
            totalAmount = totalAmount + product.getPrice() * itemDTO.getQuantity();
        }
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }
    /// get
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    /// update
    public Order updateOrderById(Order updatedOrder, Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if(optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();
            existingOrder.setOrderItems(updatedOrder.getOrderItems());
            existingOrder.setTotalAmount(updatedOrder.getTotalAmount());

            return orderRepository.save(existingOrder);
        }
        else {
            throw new EntityNotFoundException("Order not found with id " + orderId);
        }
    }
    /// delete
    public String deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
        return "Order Deleted Successfully";
    }
}

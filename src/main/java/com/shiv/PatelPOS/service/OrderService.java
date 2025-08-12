package com.shiv.PatelPOS.service;

import com.shiv.PatelPOS.dto.OrderItemsRequestDTO;
import com.shiv.PatelPOS.dto.OrderRequestDTO;
import com.shiv.PatelPOS.dto.OrderResponseDTO;
import com.shiv.PatelPOS.entity.Order;
import com.shiv.PatelPOS.entity.OrderItem;
import com.shiv.PatelPOS.entity.Product;
import com.shiv.PatelPOS.mapper.OrderMapper;
import com.shiv.PatelPOS.repository.OrderRepository;
import com.shiv.PatelPOS.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Saves a new order with its associated order items.
     */
    @Transactional
    public Order saveOrder(OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO.getPaymentMode() == null) {
            throw new IllegalArgumentException("Payment mode must be provided");
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMode(orderRequestDTO.getPaymentMode());

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (OrderItemsRequestDTO itemDTO : orderRequestDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Product not found with id " + itemDTO.getProductId()));

            // Check stock
            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product " + product.getName());
            }

            // Deduct stock
            product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
            productRepository.save(product);

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItem.setSubTotal(product.getPrice() * itemDTO.getQuantity());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
            totalAmount += orderItem.getSubTotal();
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    /**
     * Get all orders.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Updates an existing order by its ID with new order item details.
     */
    @Transactional
    public Order updateOrderById(OrderRequestDTO updatedOrder, Long orderId) {
        if (updatedOrder.getPaymentMode() == null) {
            throw new IllegalArgumentException("Payment mode must be provided");
        }

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        // Restore stock from old items
        for (OrderItem oldItem : existingOrder.getOrderItems()) {
            Product product = oldItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() + oldItem.getQuantity());
            productRepository.save(product);
        }

        existingOrder.getOrderItems().clear();

        double totalAmount = 0.0;
        List<OrderItem> newItems = new ArrayList<>();

        for (OrderItemsRequestDTO itemDTO : updatedOrder.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + itemDTO.getProductId()));

            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product " + product.getName());
            }

            // Deduct stock for updated order
            product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
            productRepository.save(product);

            double subTotal = product.getPrice() * itemDTO.getQuantity();
            totalAmount += subTotal;

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPriceAtPurchase(product.getPrice());
            item.setSubTotal(subTotal);
            item.setOrder(existingOrder);

            newItems.add(item);
        }

        existingOrder.setOrderItems(newItems);
        existingOrder.setPaymentMode(updatedOrder.getPaymentMode());
        existingOrder.setTotalAmount(totalAmount);

        return orderRepository.save(existingOrder);
    }

    /**
     * Deletes an order and restores product stock.
     */
    @Transactional
    public String deleteOrderById(Long orderId) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        for (OrderItem item : existingOrder.getOrderItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }

        orderRepository.delete(existingOrder);
        return "Order Deleted Successfully";
    }

    /**
     * Sort orders by field.
     */
    public List<OrderResponseDTO> findOrderByField(String field) {
        return orderRepository.findAll(Sort.by(field)).stream()
                .map(OrderMapper::mapOrderResponseDTO)
                .collect(Collectors.toList());
    }
}

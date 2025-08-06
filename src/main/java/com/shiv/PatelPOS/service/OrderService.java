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
    /**
     * Saves a new order with its associated order items.
     *
     * Steps:
     * 1. Create an empty order with current date.
     * 2. Loop through all items in the incoming request DTO.
     *    - Fetch each product by ID.
     *    - Check if sufficient stock is available.
     *    - Deduct stock quantity.
     *    - Create a new OrderItem with price, quantity, subtotal.
     * 3. Add all items to the order and calculate total amount.
     * 4. Persist the order to the database.
     *
     * @param orderRequestDTO the incoming request with product IDs and quantities
     * @return the saved Order object
     */
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
    /**
     * Updates an existing order by its ID with new order item details.
     *
     * Steps:
     * 1. Find the existing order using the provided orderId.
     * 2. If found, clear the old order items.
     * 3. Recalculate the total amount based on new items.
     * 4. For each item in the request:
     *    - Fetch the product from DB.
     *    - Calculate subtotal (product price × quantity).
     *    - Create a new OrderItem and associate with order.
     * 5. Set the updated item list and total to the existing order.
     * 6. Save and return the updated order.
     *
     * @param updatedOrder the new order data to update with
     * @param orderId the ID of the order to update
     * @return updated Order object
     */
    public Order updateOrderById(OrderRequestDTO updatedOrder, Long orderId) {

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("order not found"));

        // Restore product stock from old order items
        for(OrderItem oldItem: existingOrder.getOrderItems()) {
            Product product = oldItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() + oldItem.getQuantity());
            //product.getStockQuantity() is the total stock count of that product
            // & oldItem.getQuantity() is the total stock ordered in that order
        }

        // Clearing the current items
        existingOrder.getOrderItems().clear();

        double totalAmount = 0.0;
        List<OrderItem> newItems = new ArrayList<>();

        for(OrderItemsRequestDTO itemDTO: updatedOrder.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() ->
                            new EntityNotFoundException
                                    ("Product not found with id " + itemDTO.getProductId()));

            double subTotal = product.getPrice() * itemDTO.getQuantity();
            totalAmount = totalAmount + subTotal;

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPriceAtPurchase(product.getPrice());
            item.setSubTotal(subTotal);
            item.setOrder(existingOrder);

            newItems.add(item);
        }
        existingOrder.setOrderItems(newItems);
        existingOrder.setTotalAmount(totalAmount);

        return orderRepository.save(existingOrder);
    }
    /// delete
    public String deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
        return "Order Deleted Successfully";
    }
}

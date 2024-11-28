package com.demo.service;

import com.demo.entity.Cart;
import com.demo.entity.Order;
import com.demo.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Create a new Order
    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    // Update an existing Order
    public void updateOrder(Long id, Order order) {
        order.setId(id);
        orderRepository.save(order);
    }

    // Delete an Order by ID
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Create Order from Cart
    public Order saveOrder(Cart cart) {
        // Ensure the Cart has necessary fields such as customerName, totalPrice, and dishes
        Order order = new Order();
        order.setCustomerName(cart.getCustomerName());  // Ensure Cart has a customerName
        order.setTotalAmount(cart.getTotalPrice());     // Ensure Cart has a totalPrice
        order.setDishes(cart.getDishes());              // Ensure Cart has a list of Dishes

        // Save and return the Order
        return orderRepository.save(order);
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Get order by ID (with exception handling)
    public Order getOrderById1(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    // Update the status of an order (e.g., APPROVED or REJECTED)
    public void updateOrderStatus(Long id, String status) {
        Optional<Order> order = orderRepository.findById(id);
        order.ifPresent(o -> {
            o.setStatus(status);
            orderRepository.save(o);
        });
    }

    // Add a new order (this can be used for testing purposes)
    public void addOrder(Order order) {
        orderRepository.save(order);
    }
}

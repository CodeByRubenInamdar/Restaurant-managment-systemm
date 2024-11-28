package com.demo.controller;

import com.demo.entity.Dish;
import com.demo.entity.Order;
import com.demo.service.DishService;
import com.demo.service.OrderService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private DishService dishService;

    // Display all orders in the admin panel
    @GetMapping("/list")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin_order_list"; // Thymeleaf template to display orders
    }

    // Approve an order
    @PostMapping("/approve/{id}")
    public String approveOrder(@PathVariable Long id) {
        orderService.updateOrderStatus(id, "APPROVED");
        return "redirect:/admin/orders/list";
    }

    // Reject an order
    @PostMapping("/reject/{id}")
    public String rejectOrder(@PathVariable Long id) {
        orderService.updateOrderStatus(id, "REJECTED");
        return "redirect:/admin/orders/list";
    }
    
    // Method to show the bill for the order
    @GetMapping("/bill/{orderId}")
    public String showBill(@PathVariable Long orderId, Model model) {
        // Fetch the order details by orderId
        Order order = orderService.getOrderById1(orderId); // Using the method that throws an exception if not found

        // Calculate the total cost of the order (assuming order contains a list of ordered dishes with quantity)
        double totalCost = 0;
        List<Dish> dishes = order.getDishes(); // Assuming the order has a list of dishes
        for (Dish dish : dishes) {
            Integer quantity = dish.getQuantity();  // Get quantity as Integer
            
            if (quantity != null) {
                totalCost += dish.getPrice() * quantity; // Calculate the cost for each dish
            }
        }

        // Add order details and total cost to the model
        model.addAttribute("order", order);
        model.addAttribute("dishes", dishes);
        model.addAttribute("totalCost", totalCost);
        return "bill"; // Return the bill view (bill.html)
    }
    
    @GetMapping("/order/{id}")
    public String getOrderBill(@PathVariable("id") Long orderId, Model model) {
        Optional<Order> order = orderService.getOrderById(orderId); // Fetch the order from the database
        model.addAttribute("order", order);
        return "order-bill"; // Return the Thymeleaf template (order-bill.html)
    }
    
    @PostMapping("/admin/removeDish")
    public String removeDish(@RequestParam("id") Long id, Model model) {
        try {
            dishService.removeDishById(id); // This should delete the dish
            model.addAttribute("successMessage", "Dish removed successfully.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error while removing the dish.");
        }
        return "redirect:/admin/home"; // Redirect back to the admin page
    }

}

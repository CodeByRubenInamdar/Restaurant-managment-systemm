package com.demo.controller;

import com.demo.entity.Bill;
import com.demo.entity.Dish;
import com.demo.service.BillService;
import com.demo.service.DishService;
import com.demo.service.OrderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderService orderService;
    
    private final BillService billService;

    @Autowired
    public AdminController(BillService billService) {
        this.billService = billService;
    }
    @GetMapping("/bills")
    public String viewBills(Model model) {
        List<Bill> bills = billService.getAllBills();
        model.addAttribute("bills", bills);
        return "admin-bills";
    }
    @GetMapping("/admin/home")
    public String adminHome(Model model) {
        model.addAttribute("dishes", dishService.getAllDishes());
        model.addAttribute("bills", billService.getAllBills());
        return "admin-home"; // Corresponds to this HTML page
    }



    // Admin Home Page: Display all dishes and orders
    @GetMapping("/home")
    public String adminHomePage(Model model) {
        // Fetch all orders and dishes from the services
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("dishes", dishService.getAllDishes());
        return "admin_home";  // This will refer to your admin_home.html template
    }

    // Add new Dish (with name, description, and price)
    @PostMapping("/addDish")
    public String addDish(@RequestParam String name,
                          @RequestParam String description,
                          @RequestParam Double price,
                          Model model) {
        if (name.isEmpty() || description.isEmpty() || price == null || price <= 0) {
            model.addAttribute("errorMessage", "Invalid dish details. Please provide valid inputs.");
            return "admin_home";
        }
        Dish dish = new Dish();
        dish.setName(name);
        dish.setDescription(description);
        dish.setPrice(price);
        dish.setStatus("Pending"); // Default status
        dishService.addDish(dish); // Add dish to the database
        model.addAttribute("successMessage", "Dish added successfully!");
        return "redirect:/admin/home";
    }

    // Remove a dish by its ID
    @PostMapping("/removeDish")
    public String removeDish(@RequestParam("id") Long dishId, RedirectAttributes redirectAttributes) {
        try {
            dishService.removeDish(dishId); // Call service to remove the dish
            redirectAttributes.addFlashAttribute("successMessage", "Dish removed successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error removing dish.");
        }
        return "redirect:/admin/home"; // Redirect to the admin home page or the dishes list page
    }

    // Approve a Dish (Change status to 'Approved')
    @PostMapping("/approveDish")
    public String approveDish(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("errorMessage", "Dish ID is missing. Cannot approve the dish.");
            return "redirect:/admin/home";
        }
        dishService.updateDishStatus(id, "Approved");  // Update the dish status to "Approved"
        model.addAttribute("successMessage", "Dish approved successfully.");
        return "redirect:/admin/home";  // Redirect to admin home after approval
    }

    // Reject a Dish (Change status to 'Rejected')
    @PostMapping("/rejectDish")
    public String rejectDish(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("errorMessage", "Dish ID is missing. Cannot reject the dish.");
            return "redirect:/admin/home";
        }
        dishService.updateDishStatus(id, "Rejected");  // Update the dish status to "Rejected"
        model.addAttribute("successMessage", "Dish rejected successfully.");
        return "redirect:/admin/home";  // Redirect to admin home after rejection
    }
}

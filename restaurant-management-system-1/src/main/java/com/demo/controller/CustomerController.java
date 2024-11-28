package com.demo.controller;

import com.demo.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private DishService dishService;

    // Mapping for customer home page
    @GetMapping("/home")
    public String showCustomerHomePage(Model model) {
        // Get all dishes from the service and pass to the view
        model.addAttribute("dishes", dishService.getAllDishes());
        return "customer_home";  // This should be the name of your Thymeleaf template (e.g. customer_home.html)
    }

    // Other methods like addToCart as before
}

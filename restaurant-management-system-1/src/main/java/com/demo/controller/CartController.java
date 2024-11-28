package com.demo.controller;

import com.demo.entity.Cart;
import com.demo.entity.CartItem;
import com.demo.entity.Dish;
import com.demo.entity.Order;
import com.demo.service.CartService;
import com.demo.service.DishService;
import com.demo.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderService orderService;

    // Method to view the cart and display available dishes for the dropdown
    @RequestMapping("/viewCart")
    public String viewCart(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart"); // Get the cart from session
        if (cart == null) {
            cart = new Cart(); // If cart is null, create a new one
            session.setAttribute("cart", cart);
        }

        model.addAttribute("cartItems", cart.getItems());  // cart.getItems() should return List<CartItem>
        model.addAttribute("totalPrice", cart.getTotalPrice());  // Assuming cart.getTotalPrice() is a double or similar type
        model.addAttribute("dishes", dishService.getAllDishes());  // Get all dishes for the dropdown
        return "viewCart";  // Return the viewCart.html template
    }

    // Method to add a dish to the cart
    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("dishId") Long dishId, 
                            @RequestParam("quantity") int quantity, 
                            HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Get the dish by ID and add it to the cart
        Dish dish = dishService.getDishById(dishId);
        if (dish != null) {
            CartItem cartItem = new CartItem(dish, quantity);  // Create a CartItem
            cart.addItem(cartItem);  // Add the item to the cart
        }

        return "redirect:/customer/viewCart";  // Redirect to the viewCart page to show updated cart
    }

    // Method to remove a dish from the cart
    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("dishId") Long dishId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.removeItemByDishId(dishId);  // Remove item by dishId
        }

        return "redirect:/customer/viewCart";  // Redirect to the viewCart page after removing the item
    }

    // Method to generate the bill
    @PostMapping("/bill-pending")
    public String generateBill(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null && !cart.getItems().isEmpty()) {
            Order order = orderService.saveOrder(cart);  // Save the order
            session.removeAttribute("cart");  // Clear the cart from session
            model.addAttribute("order", order);  // Add order details to the model
        } else {
            model.addAttribute("message", "Cart is empty, please add items first.");
            return "viewCart";  // Redirect to the cart view if it's empty
        }
        return "bill-pending";  // Return the path to the bill-pending template
    }

    // Method to add multiple dishes to the cart
    @PostMapping("/addMultipleToCart")
    public String addMultipleToCart(@RequestParam("dishes") List<Long> dishIds, 
                                    @RequestParam("quantities") Map<Long, Integer> quantities, 
                                    HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Loop through each dishId and add the corresponding dish to the cart
        for (Long dishId : dishIds) {
            Dish dish = dishService.getDishById(dishId);
            Integer quantity = quantities.get(dishId);
            if (dish != null && quantity != null && quantity > 0) {
                CartItem cartItem = new CartItem(dish, quantity);
                cart.addItem(cartItem);  // Add the item to the cart
            }
        }

        return "redirect:/customer/viewCart";  // Redirect to the cart view after adding items
    }
}

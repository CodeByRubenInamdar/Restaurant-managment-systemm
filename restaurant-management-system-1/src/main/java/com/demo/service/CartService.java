package com.demo.service;

import com.demo.entity.Cart;
import com.demo.entity.CartItem;
import com.demo.entity.Dish;
import com.demo.repository.DishRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    // The Cart object to hold the items in the cart
    private final Cart cart = new Cart();

    // Dish repository to fetch dish details
    @Autowired
    private DishRepository dishRepository;

    // Add a dish to the cart
    public void addDishToCart(Dish dish, int quantity) {
        // Create a new CartItem with the specified dish and quantity
        CartItem cartItem = new CartItem(dish, quantity);
        
        // Add the item to the cart
        cart.addItem(cartItem);
    }

    // Remove a dish from the cart
    public void removeDishFromCart(Dish dish) {
        CartItem cartItemToRemove = null;
        
        // Loop through the cart items to find the dish
        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getDish().equals(dish)) {
                cartItemToRemove = cartItem;
                break;
            }
        }
        
        // If the dish is found, remove it from the cart
        if (cartItemToRemove != null) {
            cart.removeItem(cartItemToRemove);
        }
    }

    // Get the list of items in the cart
    public List<CartItem> getCartItems() {
        return cart.getItems();
    }

    // Get the total price of all items in the cart
    public double getTotalPrice() {
        return cart.getTotalPrice();
    }

    // Find a dish by its ID (useful for fetching dish details to add to the cart)
    public Dish findDishById(Long dishId) {
        return dishRepository.findById(dishId).orElse(null);
    }
}

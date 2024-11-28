package com.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    // List to hold CartItems
    private List<CartItem> items = new ArrayList<>();
    private String customerName; // Assuming customerName is a property of the Cart

    // Method to add a CartItem to the Cart
    public void addItem(CartItem item) {
        // Check if item is already in the cart and update the quantity if necessary
        boolean itemFound = false;
        for (CartItem cartItem : items) {
            if (cartItem.getDish().getId().equals(item.getDish().getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                itemFound = true;
                break;
            }
        }
        if (!itemFound) {
            items.add(item); // Add new item if not found
        }
    }

    // Method to remove a CartItem from the Cart by item
    public void removeItem(CartItem item) {
        items.remove(item);
    }

    // Method to remove a CartItem by dish ID (optional, in case we want to remove by dish)
    public void removeItemByDishId(Long dishId) {
        items.removeIf(item -> item.getDish().getId().equals(dishId));
    }

    // Method to calculate the total price of the cart
    public double getTotalPrice() {
        double total = 0.0;
        for (CartItem item : items) {
            total += item.getDish().getPrice() * item.getQuantity();
        }
        return total;
    }

    // Optional: Clear all items from the cart (e.g., on checkout or empty cart action)
    public void clearCart() {
        items.clear();
    }

    // Getter and Setter methods
    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Method to return all dishes in the cart
    public List<Dish> getDishes() {
        List<Dish> dishes = new ArrayList<>();
        for (CartItem item : items) {
            dishes.add(item.getDish());  // Add the dish from each cart item
        }
        return dishes;
    }
}

package com.demo.entity;

public class CartItem {
    private Dish dish;
    private int quantity;
    
    public CartItem(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }
    
    // Calculate total price based on dish price and quantity
    public double getTotalPrice() {
        return dish.getPrice() * quantity; // Assuming Dish has a getPrice() method
    }

    // Getter and setter methods for dish and quantity
    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

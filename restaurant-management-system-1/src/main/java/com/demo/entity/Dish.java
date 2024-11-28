package com.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "`dish`")
@Data
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity; // Change this from String to Integer (or Double if needed)
    private String name;
    private Double price;
    private String description;
    private String imagePath;
    private String status; // Add status field to store the dish status

    // Default constructor (necessary for Hibernate)
    public Dish() {
    }

    // Constructor with all arguments (optional)
    public Dish(String name, Double price, String description, String imagePath, String status, Integer quantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.status = status;
        this.quantity = quantity; // Set the initial quantity
    }

    // Getter and Setter methods

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Other getters and setters for name, price, description, etc.
}

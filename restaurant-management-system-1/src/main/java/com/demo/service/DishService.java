package com.demo.service;

import com.demo.entity.Dish;
import com.demo.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    // Retrieve all dishes
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    // Add a new dish
    public void addDish(Dish dish) {
        dishRepository.save(dish);
    }

    // Remove a dish by its ID
    public void removeDishById(Long id) {
        dishRepository.deleteById(id); // Ensure this deletes the dish by ID
    }

    // Find a dish by its ID
    public Dish findById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    // Update an existing dish by ID
    public void updateDish(Long id, Dish updatedDish) {
        Dish existingDish = findById(id);
        if (existingDish != null) {
            updatedDish.setId(id);
            dishRepository.save(updatedDish);
        }
    }

    // Update the status of a dish (Approve/Reject)
    public void updateDishStatus(Long id, String status) {
        Dish dish = findById(id);
        if (dish != null) {
            dish.setStatus(status); // Assuming Dish entity has a status field
            dishRepository.save(dish);
        }
    }

    // Retrieve a dish by its ID (Alias for findById)
    public Dish getDishById(Long id) {
        return findById(id);
    }

	public void removeDish(Long dishId) {
        dishRepository.deleteById(dishId); // Delete dish from database using the repository
    }
	
	public List<Dish> getAllDishe() {
        return dishRepository.findAll();
    }

	
}

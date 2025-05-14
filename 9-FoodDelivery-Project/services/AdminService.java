package com.fooddelivery.services;

import com.fooddelivery.models.DeliveryPerson;
import com.fooddelivery.models.FoodItem;
import com.fooddelivery.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class AdminService {
    private List<Restaurant> restaurants = new ArrayList<>();
    
    // Add this line (Line 6)
    private List<DeliveryPerson> deliveryPersons = new ArrayList<>(); // List to hold delivery persons

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public void addFoodItem(int restaurantId, FoodItem foodItem) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == restaurantId) {
                restaurant.addFoodItem(foodItem);
            }
        }
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
    
    // Method to add delivery person (Line 17)
    public void addDeliveryPerson(DeliveryPerson deliveryPerson) {
        deliveryPersons.add(deliveryPerson);  // Add the delivery person to the list
    }

    // Method to get delivery person by ID (Line 21)
    public DeliveryPerson getDeliveryPersonById(int deliveryPersonId) {
        for (DeliveryPerson deliveryPerson : deliveryPersons) {
            if (deliveryPerson.getDeliveryPersonId() == deliveryPersonId) {
                return deliveryPerson;
            }
        }
        return null;
    }
}

package com.fooddelivery.controllers;

import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.models.Customer;
import com.fooddelivery.models.DeliveryPerson;
import com.fooddelivery.models.FoodItem;
import com.fooddelivery.models.Order;
import com.fooddelivery.models.Restaurant;
import com.fooddelivery.services.AdminService;
import com.fooddelivery.models.FoodItem;
import com.fooddelivery.services.OrderService;  




public class AdminController {
    private AdminService adminService = new AdminService();
    private List<Restaurant> restaurants = new ArrayList<>();
    private OrderService orderService = new OrderService(); 
    private List<DeliveryPerson> deliveryPeople = new ArrayList<>();
    

    

    public void addRestaurant(int id, String name) {
        Restaurant restaurant = new Restaurant(id, name);
        restaurants.add(restaurant);
        adminService.addRestaurant(restaurant);
        System.out.println("Restaurant added: " + restaurant);
    }

    public void addFoodItem(int restaurantId, int foodItemId, String name, double price) {
        FoodItem foodItem = new FoodItem(foodItemId, name, price);
        adminService.addFoodItem(restaurantId, foodItem);
        System.out.println("Food item added to restaurant: " + foodItem);
    }

    // private List<Restaurant> restaurants;
    public void removeFoodItem(int restaurantId, int foodItemId) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (restaurant != null) {
            boolean removed = restaurant.removeFoodItem(foodItemId);
            if (removed) {
                System.out.println("Food item removed from restaurant: ID " + foodItemId);
            } else {
                System.out.println("Food item not found in the specified restaurant.");
            }
        } else {
            System.out.println("Restaurant with ID " + restaurantId + " not found.");
        }
    }

    private Restaurant findRestaurantById(int restaurantId) { 
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == restaurantId) {
                return restaurant;  // Return the restaurant if found
            }
        }
        return null;  // Return null if not found
    }

    // Method to retrieve FoodItem by Restaurant ID and Food Item ID
    public FoodItem getFoodItemById(int restId, int foodId) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == restId) {
                // Search for the food item in the restaurant's menu
                for (FoodItem foodItem : restaurant.getMenu()) {
                    if (foodItem.getId() == foodId) {
                        return foodItem;  // Return the food item if found
                    }
                }
            }
        }
        return null;  // Return null if restaurant or food item is not found
    }

    public void viewRestaurantsAndMenus() {
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available.");
            return;
        }

        System.out.println("Available Restaurants and Menus:");
        for (Restaurant restaurant : restaurants) {
            System.out.println("Restaurant: " + restaurant.getName() + " (ID: " + restaurant.getId() + ")");
            List<FoodItem> menu = restaurant.getMenu();
            if (menu.isEmpty()) {
                System.out.println("  Menu: No items available.");
            } else {
                System.out.println("  Menu:");
                for (FoodItem foodItem : menu) {
                    System.out.println("    - " + foodItem.getName() + " (ID: " + foodItem.getId() + ", Price: " + foodItem.getPrice() + ")");
                }
            }
        }
    }

    public void viewOrders() {
        List<Order> orders = orderService.getAllOrders();  // Fetch orders from OrderService
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        System.out.println("Current Orders:");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderId() + ", Customer: " + order.getCustomer().getUsername() +
                ", Total Amount: " + order.getTotalAmount() + ", Status: " + order.getStatus());
            // Print food items in the order
            System.out.println("  Food Items:");
            for (FoodItem foodItem : order.getItems().keySet()) { // Assuming getItems() returns a Map<FoodItem, Integer>
                System.out.println("    - " + foodItem.getName() + " (ID: " + foodItem.getId() + ")");
            }
        }
    }
    public void addDeliveryPerson(int id, String name, long contactNo) { 
        DeliveryPerson deliveryPerson = new DeliveryPerson(id, name, contactNo);
        deliveryPeople.add(deliveryPerson);
        System.out.println("Delivery Person added: " + deliveryPerson);
    }
    public void assignDeliveryPerson(int orderId, int deliveryPersonId) {
        Order order = orderService.getOrderById(orderId);  // Assumes getOrderById is defined in OrderService
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }
    
        DeliveryPerson deliveryPerson = adminService.getDeliveryPersonById(deliveryPersonId);
        if (deliveryPerson == null) {
            System.out.println("Delivery person not found!");
            return;
        }
    
        order.setDeliveryPerson(deliveryPerson);
        System.out.println("Delivery person assigned to order successfully.");
    }
    
}

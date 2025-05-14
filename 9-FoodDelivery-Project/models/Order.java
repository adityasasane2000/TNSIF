package com.fooddelivery.models;

import java.util.List;
import java.util.Map;
import com.fooddelivery.services.OrderService;

public class Order {
    private static int lastOrderId = 0; //added
    public int orderId=0;//changed, added =0
    public Customer customer;
    private Map<FoodItem, Integer> items;
    public String status = "Pending";
    private DeliveryPerson deliveryPerson; // Keep this single declaration
    private String deliveryAddress;
    private OrderService orderService = new OrderService();

    public Order(int orderId,Customer customer, Map<FoodItem, Integer> items, String deliveryAddress) {
        this.orderId = ++lastOrderId;
        this.customer = customer;
        this.items = items;
        this.deliveryAddress = deliveryAddress;
    }

    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public Map<FoodItem, Integer> getItems() { return items; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public DeliveryPerson getDeliveryPerson() { return deliveryPerson; } // Keep this single declaration
    public void setDeliveryPerson(DeliveryPerson deliveryPerson) { this.deliveryPerson = deliveryPerson; } // Keep this single declaration
    public String getDeliveryAddress() { return deliveryAddress; }

    // New method to view orders
    public void viewOrders() {
        List<Order> orders = orderService.getAllOrders();  // Fetch orders from OrderService
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        System.out.println("Current Orders:");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderId() + ", Customer: " + order.getCustomer().getUsername() +
                ", Total Amount: " + order.getTotalAmount() + ", Status: " + order.getStatus() + 
                ", Delivery Address: " + order.getDeliveryAddress());
            // Print food items and their quantities
            System.out.println("  Food Items:");
            for (Map.Entry<FoodItem, Integer> entry : order.getItems().entrySet()) {
                FoodItem foodItem = entry.getKey();
                int quantity = entry.getValue();
                System.out.println("    - " + foodItem.getName() + " (ID: " + foodItem.getId() + ", Price: " + foodItem.getPrice() + ", Quantity: " + quantity + ")");
            }
        }
    }

    public double getTotalAmount() {
        double total = 0.0;
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue(); // Price times quantity
        }
        return total;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", customer=" + customer + ", items=" + items + ", status=" + status + "]";
    }
}

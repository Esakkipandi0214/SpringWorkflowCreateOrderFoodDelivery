package com.example.OrdersWorkflow.activities;//package com.example.OrdersWorkflow.activities;


public class OrderActivitiesImpl implements OrderActivities {

    @Override
    public String confirmOrder(String orderId) {
        // Simulating order confirmation logic
        System.out.println("Order confirmed: " + orderId);
        return "Order " + orderId + " is confirmed.";
    }

    @Override
    public void sendEmail(String confirmation) {
        // Simulating sending email logic
        System.out.println("Sending confirmation email: " + confirmation);
    }
}
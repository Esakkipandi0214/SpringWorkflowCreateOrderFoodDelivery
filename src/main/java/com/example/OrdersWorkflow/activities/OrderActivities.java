package com.example.OrdersWorkflow.activities;//package com.example.OrdersWorkflow.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OrderActivities {

    @ActivityMethod
    String confirmOrder(String orderId);

    @ActivityMethod
    void sendEmail(String confirmation);
}
package com.example.OrdersWorkflow.workflow;//package com.example.OrdersWorkflow.workflow;

import com.example.OrdersWorkflow.activities.OrderActivities;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class OrderWorkflowImpl implements OrderWorkflow {

    private final OrderActivities activities = Workflow.newActivityStub(
            OrderActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(1))
                    .build()
    );

    @Override
    public String processOrder(String orderId) {
        String confirmation = activities.confirmOrder(orderId);
        activities.sendEmail(confirmation);
        return confirmation;
    }
}

package com.example.OrdersWorkflow.service;//package com.example.OrdersWorkflow.service;

import com.example.OrdersWorkflow.workflow.OrderWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final WorkflowClient workflowClient;

    public OrderService(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    public String startOrderWorkflow(String orderId) {
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue("OrderTaskQueue")
                .build();

        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class, options);

        // Start workflow asynchronously and return immediately
        WorkflowClient.start(workflow::processOrder, orderId);

        return "Order workflow started for: " + orderId;
    }
}

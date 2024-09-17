package com.example.OrdersWorkflow.workflow;//package com.example.OrdersWorkflow.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderWorkflow {

    @WorkflowMethod
    String processOrder(String orderId);
}
package com.example.OrdersWorkflow.config;//package com.example.OrdersWorkflow.config;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.Worker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {

    @Bean
    public WorkflowClient workflowClient() {
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        return WorkflowClient.newInstance(service);
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient workflowClient) {
        return WorkerFactory.newInstance(workflowClient);
    }

    @Bean
    public Worker worker(WorkerFactory workerFactory) {
        Worker worker = workerFactory.newWorker("OrderTaskQueue");
        worker.registerWorkflowImplementationTypes(com.example.OrdersWorkflow.workflow.OrderWorkflowImpl.class);
        worker.registerActivitiesImplementations(new com.example.OrdersWorkflow.activities.OrderActivitiesImpl());
        workerFactory.start();
        return worker;
    }
}

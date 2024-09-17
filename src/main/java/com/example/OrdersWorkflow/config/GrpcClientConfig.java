package com.example.OrdersWorkflow.config;

import com.example.RestController.ItemRequest;
import com.example.RestController.ItemResponse;
import com.example.RestController.ItemServiceGrpc;
import com.example.OrdersWorkflow.model.Food;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class GrpcClientConfig {

    private final ItemServiceGrpc.ItemServiceBlockingStub itemServiceStub;
    private final ManagedChannel channel;

    public GrpcClientConfig() {
        // Create a gRPC channel and stub
        this.channel = ManagedChannelBuilder.forAddress("localhost", 9093)
                .keepAliveWithoutCalls(true)
                .idleTimeout(30, TimeUnit.MINUTES)
                .usePlaintext()
                .build();
        this.itemServiceStub = ItemServiceGrpc.newBlockingStub(channel);
        System.out.println("gRPC channel created");
    }

    // Method to send food data to the gRPC server
    public String[] addFoodViaGrpc(Food food) {
        try {
            ItemRequest request = ItemRequest.newBuilder()
                    .setName(food.getName())
                    .setPrice(food.getPrice())
                    .build();

            ItemResponse response = itemServiceStub.receiveItem(request);
            String message = response.getMessage();
            long productId = response.getId(); // Changed from int to long
            return new String[]{message, String.valueOf(productId)};
        } catch (StatusRuntimeException e) {
            // Handle gRPC exceptions
            e.printStackTrace();
            return new String[]{"Error", e.getStatus().getDescription()};
        }
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            System.out.println("Shutting down gRPC channel");
            channel.shutdown();
            try {
                if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.err.println("Channel did not terminate in the specified time.");
                    channel.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                channel.shutdownNow();
            }
        }
    }
}

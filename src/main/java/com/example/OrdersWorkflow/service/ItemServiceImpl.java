package com.example.OrdersWorkflow.service;

import com.example.OrdersWorkflow.config.GrpcClientConfig;
import com.example.RestController.ItemRequest;
import com.example.RestController.ItemResponse;
import com.example.RestController.ItemServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import com.example.OrdersWorkflow.model.Food;


@Service
public class ItemServiceImpl extends ItemServiceGrpc.ItemServiceImplBase {

    private final OrderService orderService;
    private final GrpcClientConfig grpcClientConfig;

    // Constructor without Food
    public ItemServiceImpl(OrderService orderService, GrpcClientConfig grpcClientConfig) {
        this.orderService = orderService;
        this.grpcClientConfig = grpcClientConfig;
    }

    @Override
    public void receiveItem(ItemRequest request, StreamObserver<ItemResponse> responseObserver) {
        // Process the request and generate a response
        System.out.println("Received Item: " + request.getName() + ", Price: " + request.getPrice());

        // Create a Food object from the request
        // Directly instantiate and use Food
        Food food = new Food();
        food.setName(request.getName());
        food.setPrice(request.getPrice());


        // Pass the Food object to the gRPC client method
        String[] grpcResponse = grpcClientConfig.addFoodViaGrpc(food);

        // Use the response from the gRPC client method
        String message = grpcResponse[0];
        long productId = Long.parseLong(grpcResponse[1]);

        // Start order workflow (or other operations)
        orderService.startOrderWorkflow(String.valueOf(productId));

        // Create a response to send back to the client
        ItemResponse response = ItemResponse.newBuilder()
                .setMessage(message)
                .setId(productId)
                .build();

        // Send the response
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void receiveOrderItem(ItemRequest request, StreamObserver<ItemResponse> responseObserver) {
        // Process the received item (e.g., store it in a database)
        String message = "Received item: " + request.getName() + " with price " + request.getPrice();

        ItemResponse response = ItemResponse.newBuilder()
                .setMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

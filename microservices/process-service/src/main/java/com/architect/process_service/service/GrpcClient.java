package com.architect.process_service.service;

import com.architect.common.grpc.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class GrpcClient {
    private final HelloServiceGrpc.HelloServiceStub helloServiceStub;

    public GrpcClient() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext().build();
        helloServiceStub = HelloServiceGrpc.newStub(managedChannel);
    }

    public void callService() {
//        HelloRequest request = HelloRequest.newBuilder().setName("test").build();
//        helloServiceStub.sayHello(request, new StreamObserver<HelloResponse>() {
//            @Override
//            public void onNext(HelloResponse value) {
//                System.out.println(value.getMessage());
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.out.println(t.getMessage());
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("grpc completed");
//            }
//        });
    }
}

package com.architect.data_service.service;

import hello.HelloRequest;
import hello.HelloResponse;
import hello.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class GrpcCall extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String name = request.getName();
        HelloResponse response = HelloResponse.newBuilder().setMessage(name).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

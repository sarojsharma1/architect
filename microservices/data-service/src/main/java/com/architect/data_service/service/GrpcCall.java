package com.architect.data_service.service;

import com.architect.common.grpc.HelloRequest;
import com.architect.common.grpc.HelloResponse;
import com.architect.common.grpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class GrpcCall extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String name = request.getName();
        HelloResponse helloResponse = HelloResponse.newBuilder().setMessage(name).build();
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }
}
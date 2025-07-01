package com.architect.data_service.service;

import com.architect.common.grpc.HelloRequest;
import com.architect.common.grpc.HelloResponse;
import com.architect.common.grpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class GrpcServer extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String name = request.getName();
        HelloResponse response = HelloResponse.newBuilder().setMessage(name).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
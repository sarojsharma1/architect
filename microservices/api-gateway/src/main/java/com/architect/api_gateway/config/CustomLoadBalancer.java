package com.architect.api_gateway.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;

public class CustomLoadBalancer implements ReactorLoadBalancer<ServiceInstance> {
    private final ServiceInstanceListSupplier supplier;
    private final String serviceId;

    public CustomLoadBalancer(ServiceInstanceListSupplier supplier,
                              String serviceId) {
        this.supplier = supplier;
        this.serviceId = serviceId;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        if (supplier == null) return Mono.just(new EmptyResponse());
        return supplier.get().next().map((instances) -> {
            if (instances.isEmpty()) return new EmptyResponse();
            ServiceInstance selectedInstance = chooseLeastLoaded(instances);
            return new DefaultResponse(selectedInstance);
        });
    }

    public ServiceInstance chooseLeastLoaded(List<ServiceInstance> instances) {
        instances.stream().map(
                instance -> {
                    String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/actuator/health";
                    //core logic
                    System.out.println(url);
                    return null;
                });
        return instances.get(0);
    }
}

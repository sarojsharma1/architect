package com.architect.api_gateway.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@LoadBalancerClient(name = "data-service", configuration = CustomLoadBalancerConfig.class)
public class LoadBalancerConfigRegistrar {
}
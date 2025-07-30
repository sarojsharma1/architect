package com.architect.api_gateway.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class CustomLoadBalancerConfig {
    @Bean
    public ReactorLoadBalancer<ServiceInstance> customLoadBalancer(LoadBalancerClientFactory clientFactory,
                                                                   Environment environment) {
        String serviceId = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        System.out.println(serviceId);
        serviceId = "data-service";
        ObjectProvider<ServiceInstanceListSupplier> provider =
                clientFactory.getLazyProvider(serviceId, ServiceInstanceListSupplier.class);
        ServiceInstanceListSupplier supplier = provider.getIfAvailable();
        return new CustomLoadBalancer(supplier, serviceId);
    }
}

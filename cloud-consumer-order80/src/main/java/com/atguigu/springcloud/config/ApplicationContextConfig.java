package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced//使用注解  ，赋予resttemplate负载均衡的能力。因为访问的服务是集群
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

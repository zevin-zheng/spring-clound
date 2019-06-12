package com.zhengwei.clound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//EnableEurekaClient表明为EurekaClient
@EnableEurekaClient
//EnableDiscoveryClient向服务中心注册发现
@EnableDiscoveryClient
public class CloundConsumerRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloundConsumerRibbonApplication.class, args);
    }

    //LoadBalanced 注解表明restTemplate使用LoadBalancerClient执行请求
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

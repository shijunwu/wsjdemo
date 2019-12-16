package com.cn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.cn.springcloud.service")
public class ConsumerHystrixApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerHystrixApp.class,args);
    }
}

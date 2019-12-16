package com.wsj.consul;

import com.wsj.consul.config.StudentConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties
//@EnableConfigurationProperties({StudentConfig.class})
public class ConsulApp {

    public static void main(String[] args) {
        SpringApplication.run(ConsulApp.class,args);
    }
}

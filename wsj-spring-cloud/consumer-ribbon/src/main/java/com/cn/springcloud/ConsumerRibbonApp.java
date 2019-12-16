package com.cn.springcloud;

import com.cn.ribbon.config.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@RibbonClient(name ="PROVIDER-PRODUCT" ,configuration = RibbonConfig.class) //指定项目配置ribbon路由规则
public class ConsumerRibbonApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerRibbonApp.class,args);
    }
}

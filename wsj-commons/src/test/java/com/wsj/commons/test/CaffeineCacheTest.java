package com.wsj.commons.test;

import com.wsj.cache.caffeine.UseCacheBean;
import com.wsj.cache.caffeine.CaffeineConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CaffeineCacheTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(CaffeineConfig.class);
        UseCacheBean c=(UseCacheBean) app.getBean("testCache");
        System.out.println(c.testCach("test001"));
        for(int i=0;i<20;i++) {
            System.out.println(c.testCach("test001"));
        }
        System.out.println(c.testCach("test002"));
        app.close();
    }




}

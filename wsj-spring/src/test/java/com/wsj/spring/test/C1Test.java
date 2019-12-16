package com.wsj.spring.test;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class C1Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:test.xml");
        String [] beanName = applicationContext.getBeanDefinitionNames();
        System.out.println(Arrays.toString(beanName));
    }
}

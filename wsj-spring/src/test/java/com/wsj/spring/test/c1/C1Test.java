package com.wsj.spring.test.c1;

import com.wsj.spring.c2.ComponentScanConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class C1Test {

    @Test
    public void test(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        String[] beanNames= applicationContext.getBeanDefinitionNames();
    }
}

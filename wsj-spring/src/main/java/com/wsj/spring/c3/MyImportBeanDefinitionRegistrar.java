package com.wsj.spring.c3;

import com.wsj.spring.bean.Pig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * AnnotationMetadata:当前类的注解信息
     * @param importingClassMetadata  当前类的注解信息
     * @param registry BeanDefinition注册类  把所有需要添加到容器中的bean加入;
     */
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinition root = new RootBeanDefinition(Pig.class);
        registry.registerBeanDefinition("myPig",root);
    }
}

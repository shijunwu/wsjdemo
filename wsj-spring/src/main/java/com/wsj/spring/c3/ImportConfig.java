package com.wsj.spring.c3;

import com.wsj.spring.bean.Tigger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MyFactoryBean.class,MyImportSelector.class,MyImportBeanDefinitionRegistrar.class, Tigger.class})
public class ImportConfig {
}

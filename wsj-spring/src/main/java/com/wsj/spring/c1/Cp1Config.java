package com.wsj.spring.c1;

import com.wsj.spring.bean.B;
import com.wsj.spring.bean.Tigger;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * spring 配置 类似 spring.xml
 *
 */
@Configuration
public class Cp1Config {
    /**
     *
     * @Bean 相当于 <bean ></bean>
     * 相比于xml注入bean 对应的注解
     * @return
     */
    @Lazy(value = false)  //是否懒加载
    @Primary    //同一类型，除了制定注入beanName的，都使用这个注解标示的bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @DependsOn(value = {"b"})  //指定bean实例化完成后在实例化自己  值得注意是如果指定的bean是懒加载的，那么你的懒加载配置就失效了
    @Conditional(MyConditional.class) //指定条件注入到容器 如果条件为false 不实例化bean
    @Bean(name = {"t1","t2"},autowire = Autowire.BY_NAME,initMethod = "init",destroyMethod = "destroy")
    public Tigger tigger(){
        return new Tigger();
    }

    /**
     * 懒加载失效
     * @return
     */
    @Bean
    @Lazy
    public B b(){
        return new B();
    }
}

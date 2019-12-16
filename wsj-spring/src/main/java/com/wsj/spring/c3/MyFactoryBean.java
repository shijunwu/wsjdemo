package com.wsj.spring.c3;

import com.wsj.spring.bean.Pig;
import org.springframework.beans.factory.FactoryBean;

/**
 * 使用FactoryBean 注册bean
 */
public class MyFactoryBean implements FactoryBean<Pig> {
    /**
     * 获取实例类型
     * @return
     */
    public Class<Pig> getObjectType() {
        return Pig.class;
    }

    /**
     * 获取实例对象
     * @return
     * @throws Exception
     */
    public Pig getObject() throws Exception {
        return new Pig();
    }

    /**
     * 是否单利
     * @return
     */
    public boolean isSingleton() {
        return true;
    }
}

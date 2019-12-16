package com.wsj.cache.caffeine;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.concurrent.TimeUnit;

/**
 * caffeine 缓存使用
 * @CacheConfig(cacheNames="CacheBean") 配置cache名称为 useCacheBean
 * 没有缓存情况下会睡眠5秒
 */
@CacheConfig(cacheNames="useCacheBean") //通用配置
public class UseCacheBean {

    @Cacheable(key = "#testKey",sync = true)
    public String  testCach(String testKey){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sdghjdflgkaldf;gdflf"+testKey;
    }
}

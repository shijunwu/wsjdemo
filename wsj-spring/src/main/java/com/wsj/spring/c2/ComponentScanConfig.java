package com.wsj.spring.c2;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;

/**
 * //－－－－扫描规则如下
 * //FilterType.ANNOTATION：按照注解
 * //FilterType.ASSIGNABLE_TYPE：按照给定的类型；比如按BookService类型
 * //FilterType.ASPECTJ：使用ASPECTJ表达式
 * //FilterType.REGEX：使用正则指定
 * //FilterType.CUSTOM：使用自定义规则，自已写类，实现TypeFilter接口
 */
@ComponentScan(value = {"com.wsj.spring.bean","com.wsj.spring.c2"},
        includeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM,classes =MyFilter.class )}, //引入过滤
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = Service.class)}, //排除过滤
        useDefaultFilters = false
)
public class ComponentScanConfig {
}

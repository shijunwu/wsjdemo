package com.wsm.zuul.gateway.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;


public class MyZuulFilter extends ZuulFilter {
    /**
     * 在进行Zuul过滤的时候可以设置其过滤执行的位置，那么此时有如下几种类型：
     * pre：在请求发出之前执行过滤，如果要进行访问，肯定在请求前设置头信息
     * route：在进行路由请求的时候被调用；
     * post：在路由之后发送请求信息的时候被调用；
     * error：出现错误之后进行调用
     * @return
     */
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    public int filterOrder() {
        return 0;
    }

    /**
     * 此方法返回的“true”表示应调用run（）方法
     * @return
     */
    public boolean shouldFilter() {
        return true;
    }

    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext() ; // 获取当前请求的上下文
        currentContext.addZuulRequestHeader("Authorization", "dsfdg");
        System.out.println("执行了自定义过滤器");
        return null;
    }
}

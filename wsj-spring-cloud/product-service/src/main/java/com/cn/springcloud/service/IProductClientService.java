package com.cn.springcloud.service;

import com.cn.springcloud.config.FeignClientConfig;
import com.cn.springcloud.service.failback.ProductClientServiceFallbackFactory;
import com.cn.wsj.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "PROVIDER-PRODUCT",configuration = FeignClientConfig.class,
        fallbackFactory = ProductClientServiceFallbackFactory.class)
public interface IProductClientService {
    @RequestMapping("/product/get/{id}")
    public Product getProduct(@PathVariable("id")long id);

    @RequestMapping("/product/list")
    public List<Product> listProduct() ;

    @RequestMapping("/product/add")
    public boolean addPorduct(Product product) ;
}

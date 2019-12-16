package com.cn.wsj.springcloud.mapper;


import com.cn.wsj.domain.Product;

import java.util.List;

public interface ProductMapper {
    boolean create(Product product);
    Product findById(Long id);
    List<Product> findAll();
}
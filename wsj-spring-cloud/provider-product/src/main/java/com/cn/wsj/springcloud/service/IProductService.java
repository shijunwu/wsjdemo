package com.cn.wsj.springcloud.service;


import com.cn.wsj.domain.Product;

import java.util.List;

public interface IProductService {
    Product get(long id);
    boolean add(Product product);
    List<Product> list();
}
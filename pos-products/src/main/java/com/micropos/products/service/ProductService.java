package com.micropos.products.service;

import com.micropos.products.model.Product;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductService {


    public List<Product> products();

    public Product getProduct(String id);

    public Product randomProduct();
}

package com.micropos.products.repository;


import com.micropos.products.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    public Flux<Product> allProducts();

    public Mono<Product> findProduct(String productId);

}
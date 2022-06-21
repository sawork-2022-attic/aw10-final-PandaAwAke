package com.micropos.products.service;

import com.micropos.products.model.Product;
import com.micropos.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> products() {
        return productRepository.allProducts().timeout(Duration.ofMillis(5000)).collectList().block();
    }

    @Override
    public Product getProduct(String id) {
        Product invalidProduct = new Product();

        Product result = productRepository.findProduct(id)
                .onErrorReturn(NoSuchElementException.class, invalidProduct)
                .onErrorReturn(IndexOutOfBoundsException.class, invalidProduct)
                .timeout(Duration.ofMillis(5000)).block();

        if (result == invalidProduct) {
            return null;
        }
        return result;
    }

    @Override
    public Product randomProduct() {
        List<Product> productList = products();
        return productList.get(ThreadLocalRandom.current().nextInt(0, productList.size()));
    }
}

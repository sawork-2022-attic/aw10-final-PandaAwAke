package com.micropos.carts.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Order;
import com.micropos.carts.model.OrderResult;
import com.micropos.carts.model.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@Service
@Component
public class RemoteServicesImpl implements RemoteServices {

    private final RestTemplate restTemplate;

    // Remote services
    private String productServiceUrl;
    private String orderServiceUrl;
    private String deliveryServiceUrl;


    public RemoteServicesImpl() {
        restTemplate = new RestTemplate();
    }


    // Product Service
    @Cacheable(value = "products")
    @Override
    public List<Product> getProducts() {
        String apiUrl = productServiceUrl + "/api/products";
        ResponseEntity<List> products_raw = restTemplate.getForEntity(apiUrl, List.class);
        if (products_raw.getStatusCode() != HttpStatus.OK) {
            Logger.getGlobal().warning("Could not get products, please check the products server!");
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(products_raw.getBody(), new TypeReference<>() {});
    }

    @Cacheable(key = "#productId", value = "getProduct")
    @Override
    public Product getProduct(String productId) {
        String apiUrl = productServiceUrl + "/api/products/" + productId;
        ResponseEntity<Product> productResponseEntity = restTemplate.getForEntity(apiUrl, Product.class);
        if (productResponseEntity.getStatusCode() != HttpStatus.OK) {
            Logger.getGlobal().warning("Could not get product " + productId + ", please check the products server!");
            return null;
        }
        return productResponseEntity.getBody();
    }


    // Order Service
    @Override
    public Order postCart(Cart cart) {
        String apiUrl = orderServiceUrl + "/api/orders";
        ResponseEntity<Order> orderResponseEntity = restTemplate.postForEntity(apiUrl, cart, Order.class);
        if (orderResponseEntity.getStatusCode() != HttpStatus.OK) {
            Logger.getGlobal().warning("Could not post cart, please check the order server!");
            return null;
        }
        return orderResponseEntity.getBody();
    }


    // Delivery Service
    @Override
    public OrderResult getOrderResult(int orderId) {
        String apiUrl = deliveryServiceUrl + "/api/orders/" + orderId;
        ResponseEntity<OrderResult> orderResultResponseEntity = restTemplate.getForEntity(apiUrl, OrderResult.class);
        if (orderResultResponseEntity.getStatusCode() != HttpStatus.OK) {
            Logger.getGlobal().warning("Could not get order result, please check the delivery server!");
            return null;
        }
        return orderResultResponseEntity.getBody();
    }







    public String getProductServiceUrl() {
        return productServiceUrl;
    }

    public void setProductServiceUrl(String productServiceUrl) {
        this.productServiceUrl = productServiceUrl;
    }

    public String getOrderServiceUrl() {
        return orderServiceUrl;
    }

    public void setOrderServiceUrl(String orderServiceUrl) {
        this.orderServiceUrl = orderServiceUrl;
    }

    public String getDeliveryServiceUrl() {
        return deliveryServiceUrl;
    }

    public void setDeliveryServiceUrl(String deliveryServiceUrl) {
        this.deliveryServiceUrl = deliveryServiceUrl;
    }

}

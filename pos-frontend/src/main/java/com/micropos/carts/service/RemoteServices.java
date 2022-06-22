package com.micropos.carts.service;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Order;
import com.micropos.carts.model.OrderResult;
import com.micropos.carts.model.Product;

import java.util.List;

public interface RemoteServices {

    // Product Service
    public List<Product> getProducts();
    public Product getProduct(String productId);

    // Order Service
    public Order postCart(Cart cart);

    // Delivery Service
    public OrderResult getOrderResult(int orderId);

}

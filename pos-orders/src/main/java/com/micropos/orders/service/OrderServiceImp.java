package com.micropos.orders.service;

import com.micropos.orders.model.Cart;
import com.micropos.orders.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImp implements OrderService {

    @Autowired
    private StreamBridge streamBridge;

    private int idCounter = 10000;

    @Override
    synchronized public Order generateOrder(Cart cart) {
        Order order = new Order(cart, idCounter++);
        streamBridge.send("order-service", order);
        return order;
    }
}

package com.micropos.orders.service;

import com.micropos.orders.model.Cart;

public interface OrderService {
    public void generateOrder(Cart cart);
}

package com.micropos.orders.service;

import com.micropos.orders.model.Cart;
import com.micropos.orders.model.Order;

public interface OrderService {
    public Order generateOrder(Cart cart);
}

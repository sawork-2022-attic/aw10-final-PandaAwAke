package com.micropos.carts.service;

import com.micropos.carts.model.Cart;

import java.util.List;

public interface CartsService {

    public Cart getCart();

    public boolean putProduct(String productId, int quantity);
    public boolean deleteProduct(String productId, int quantity);
    public int getProductQuantity(String productId);

}

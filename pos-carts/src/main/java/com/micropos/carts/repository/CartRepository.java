package com.micropos.carts.repository;

import com.micropos.carts.model.Cart;

public interface CartRepository {

    public Cart getCart();

    public boolean putProduct(String productId, int quantity);
    public boolean deleteProduct(String productId, int quantity);
    public int getProductQuantity(String productId);

}

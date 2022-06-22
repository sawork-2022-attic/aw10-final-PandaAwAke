package com.micropos.carts.service;

import com.micropos.carts.model.Product;
import com.micropos.carts.model.ProductCart;

public interface CartsService {

    // Cart operations for session and display use
    public void addProduct(ProductCart cart, Product product, int quantity);
    public boolean removeProduct(ProductCart cart, String productId);
    public void clearCart(ProductCart cart);
    public int getProductQuantity(ProductCart cart, String productId);
    public double calculateTotalPrice(ProductCart cart);

}

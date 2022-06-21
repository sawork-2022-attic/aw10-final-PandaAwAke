package com.micropos.carts.service;

import com.micropos.carts.model.Cart;
import com.micropos.carts.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartsServiceImpl implements CartsService {

    private CartRepository cartRepository;

    public CartsServiceImpl(@Autowired CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart getCart() {
        return cartRepository.getCart();
    }

    @Override
    public boolean putProduct(String productId, int quantity) {
        return cartRepository.putProduct(productId, quantity);
    }

    @Override
    public boolean deleteProduct(String productId, int quantity) {
        return cartRepository.deleteProduct(productId, quantity);
    }

    @Override
    public int getProductQuantity(String productId) {
        return cartRepository.getProductQuantity(productId);
    }


}

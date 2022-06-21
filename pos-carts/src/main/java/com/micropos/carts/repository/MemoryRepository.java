package com.micropos.carts.repository;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryRepository implements CartRepository {

    private Cart cart;

    public MemoryRepository() {
        cart = new Cart();
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public boolean putProduct(String productId, int quantity) {
        return getCart().addItem(new Item(productId, quantity));
    }

    @Override
    public boolean deleteProduct(String productId, int quantity) {
        int productIndex = -1;
        int remainingQuantity = -1;

        for (int i = 0; i < getCart().getItems().size(); i++) {
            Item item = getCart().getItems().get(i);
            if (item.getProductId().equals(productId)) {
                productIndex = i;
                remainingQuantity = item.getQuantity();
                break;
            }
        }

        if (productIndex == -1) {
            return false;
        }

        if (remainingQuantity <= quantity) {
            getCart().getItems().remove(productIndex);
        } else {
            getCart().getItems().get(productIndex).setQuantity(remainingQuantity - quantity);
        }

        return true;
    }

    @Override
    public int getProductQuantity(String productId) {
        for (Item item : getCart().getItems()) {
            if (item.getProductId().equals(productId) && item.getQuantity() > 0) {
                return item.getQuantity();
            }
        }
        return 0;
    }
}

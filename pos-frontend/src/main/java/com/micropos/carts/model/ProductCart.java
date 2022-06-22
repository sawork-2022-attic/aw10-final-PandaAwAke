package com.micropos.carts.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class ProductCart {

    private List<ProductItem> items = new ArrayList<>();

    public boolean addItem(ProductItem item) {
        return items.add(item);
    }

    public List<ProductItem> getItems() {
        return items;
    }

    public Cart toCart() {
        Cart cart = new Cart();
        for (ProductItem productItem : items) {
            cart.getItems().add(productItem.toItem());
        }
        return cart;
    }

}

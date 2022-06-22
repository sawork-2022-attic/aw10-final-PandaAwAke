package com.micropos.carts.service;

import com.micropos.carts.model.Product;
import com.micropos.carts.model.ProductCart;
import com.micropos.carts.model.ProductItem;
import org.springframework.stereotype.Service;

@Service
public class CartsServiceImpl implements CartsService {

    @Override
    public void addProduct(ProductCart productCart, Product product, int amount) {
        for (ProductItem productItem : productCart.getItems()) {
            if (productItem.getProduct().getId().equals(product.getId())) {
                int quantity = productItem.getQuantity() + amount;
                if (quantity <= 0) {
                    productCart.getItems().remove(productItem);
                } else {
                    productItem.setQuantity(quantity);
                }
                return;
            }
        }
        if (amount > 0) {
            productCart.getItems().add(new ProductItem(product, amount));
        }
    }

    @Override
    public boolean removeProduct(ProductCart productCart, String productId) {
        for (ProductItem productItem : productCart.getItems()) {
            if (productItem.getProduct().getId().equals(productId)) {
                productCart.getItems().remove(productItem);
                return true;
            }
        }
        return false;
    }

    @Override
    public void clearCart(ProductCart productCart) {
        productCart.getItems().clear();
    }

    @Override
    public int getProductQuantity(ProductCart productCart, String productId) {
        for (ProductItem productItem : productCart.getItems()) {
            if (productItem.getProduct().getId().equals(productId)) {
                return productItem.getQuantity();
            }
        }
        return 0;
    }

    @Override
    public double calculateTotalPrice(ProductCart productCart) {
        double total = 0;
        for (ProductItem productItem : productCart.getItems()) {
            total += productItem.getQuantity() * productItem.getProduct().getPrice();
        }
        return total;
    }

}

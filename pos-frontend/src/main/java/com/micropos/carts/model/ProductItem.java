package com.micropos.carts.model;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductItem {

    private Product product;
    private int quantity;

    public ProductItem() {
    }

    public ProductItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Item toItem() {
        Item item = new Item();
        item.setProductId(product.getId());
        item.setQuantity(quantity);
        return item;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductItem item = (ProductItem) o;
        return quantity == item.quantity && product.equals(item.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

}

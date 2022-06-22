package com.micropos.orders.model;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Objects;

@Component
public class Item implements Serializable {
    private String productId;
    @Min(1)
    private int quantity;

    public Item() {
    }

    public Item(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
        Item item = (Item) o;
        return quantity == item.quantity && productId.equals(item.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity);
    }
}

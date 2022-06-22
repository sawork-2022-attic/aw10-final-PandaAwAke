package com.micropos.orders.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Component
public class Order implements Serializable {
    private List<Item> items;
    private long timestamp;
    private int id;

    public Order() {
        this.timestamp = System.currentTimeMillis();
    }

    public Order(Cart cart, int id) {
        this.items = cart.getItems();
        this.timestamp = System.currentTimeMillis();
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return timestamp == order.timestamp && id == order.id && Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, timestamp, id);
    }
}

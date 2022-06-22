package com.micropos.carts.model;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderResult {
    public enum OrderStatus {
        OK, FAILED, INVALID
    }
    private int orderId;
    private final long timestamp;
    private OrderStatus status;


    public OrderResult() {
        this(OrderStatus.INVALID);
    }

    public OrderResult(OrderStatus status) {
        this(-1, status);
    }

    public OrderResult(int orderId, OrderStatus status) {
        this.orderId = orderId;
        this.timestamp = System.currentTimeMillis();
        this.status = status;
    }

    public OrderResult(Order order) {
        this.timestamp = System.currentTimeMillis();
        if (order == null) {
            this.orderId = -1;
            this.status = OrderStatus.INVALID;
        } else {
            this.orderId = order.getId();
            this.status = OrderStatus.OK;
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isOK() {
        return this.status == OrderStatus.OK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderResult that = (OrderResult) o;
        return orderId == that.orderId && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, status);
    }

    @Override
    public String toString() {
        String str = "This order ";
        switch (status) {
            case OK -> str += "is OK!";
            case FAILED -> str += "is Failed!";
            case INVALID -> str += "doesn't exist!";
        }
        return str;
    }

}

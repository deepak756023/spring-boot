package com.practice.Assignment.entities.store;

import jakarta.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class OrderItemId implements Serializable {

    private int orderId;
    private int productId;

    public OrderItemId(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public OrderItemId() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderItemId{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                '}';
    }
}

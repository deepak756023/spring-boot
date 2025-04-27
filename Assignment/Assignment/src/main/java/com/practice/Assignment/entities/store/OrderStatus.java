package com.practice.Assignment.entities.store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_statuses")
public class OrderStatus {

    @Id
    @Column(name = "order_status_id")
    private int orderId;

    @Column(name = "name")
    private String name;

    public OrderStatus(String name, int orderId) {
        this.name = name;
        this.orderId = orderId;
    }

    public OrderStatus() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "name='" + name + '\'' +
                ", orderId=" + orderId +
                '}';
    }


}

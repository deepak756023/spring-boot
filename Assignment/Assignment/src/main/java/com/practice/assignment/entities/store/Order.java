package com.practice.assignment.entities.store;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer costumer;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "status")
    private OrderStatus orderStatus;

    @Column(name = "comments")
    private String comment;

    @Column(name = "shipped_date")
    private LocalDate shippedDate;

    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private Shippers shipper;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Customer getCostumer() {
        return costumer;
    }

    public void setCostumer(Customer costumer) {
        this.costumer = costumer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDate shippedDate) {
        this.shippedDate = shippedDate;
    }

    public Shippers getShipper() {
        return shipper;
    }

    public void setShipper(Shippers shipper) {
        this.shipper = shipper;
    }

    public Order(int orderId, Customer costumer, LocalDate orderDate, OrderStatus orderStatus, String comment,
            LocalDate shippedDate, Shippers shipper) {
        this.orderId = orderId;
        this.costumer = costumer;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.comment = comment;
        this.shippedDate = shippedDate;
        this.shipper = shipper;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return "orders [orderId=" + orderId + ", costumer=" + costumer + ", orderDate=" + orderDate + ", orderStatus="
                + orderStatus + ", comment=" + comment + ", shippedDate=" + shippedDate + ", shipper=" + shipper + "]";
    }

}

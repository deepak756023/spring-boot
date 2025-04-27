package com.practice.Assignment.entities.store;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "order_items")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @ManyToOne
    @MapsId("orderId") // Maps to OrderItemId.orderId
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId") // Maps to OrderItemId.productId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price")
    private float unitPrice;

    public OrderItem(OrderItemId id, float unitPrice, int quantity, Product product, Order order) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.product = product;
        this.order = order;
    }

    public OrderItem() {
    }

    public OrderItemId getId() {
        return id;
    }

    public void setId(OrderItemId id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + order +
                ", product=" + product +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

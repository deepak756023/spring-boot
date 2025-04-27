package com.practice.Assignment.entities.store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "name")
    private String name;

    @Column(name = "Quantity_in_stock")
    private int quantityInStock;

    @Column(name = "unit_price")
    private float unitPrice;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Product(int productId, String name, int quantityInStock, float unitPrice) {
        this.productId = productId;
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.unitPrice = unitPrice;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", name=" + name + ", quantityInStock=" + quantityInStock
                + ", unitPrice=" + unitPrice + "]";
    }

}

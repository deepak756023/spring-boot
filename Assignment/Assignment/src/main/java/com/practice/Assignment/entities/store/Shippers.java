package com.practice.Assignment.entities.store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "shippers")
public class Shippers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shipper_id")
    private int shipperId;

    @Column(name = "name")
    private String name;

    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shippers(int shipperId, String name) {
        this.shipperId = shipperId;
        this.name = name;
    }

    public Shippers() {
    }

    @Override
    public String toString() {
        return "Shippers [shipperId=" + shipperId + ", name=" + name + "]";
    }

}

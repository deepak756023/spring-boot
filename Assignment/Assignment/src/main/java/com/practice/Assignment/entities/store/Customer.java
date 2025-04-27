package com.practice.Assignment.entities.store;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "points")
    private int points;

    public Customer(String address, LocalDate birthDate, String city, int customerId, String lastName, String firstName, String phone, int points, String state) {
        this.address = address;
        this.birthDate = birthDate;
        this.city = city;
        this.customerId = customerId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.points = points;
        this.state = state;
    }

    public Customer() {
    }

    public String getAddress() {
        return address;
    }

    public String getState() {
        return state;
    }

    public int getPoints() {
        return points;
    }

    public String getPhone() {
        return phone;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "address='" + address + '\'' +
                ", customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", points=" + points +
                '}';
    }
}

package com.practice.assignment.entities.store;

import jakarta.persistence.*;

@Entity
@Table(name = "user_password")
public class UserPassword {

    @Id
    private int id; // separate PK

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", unique = true)
    private User user;

    @Column(name = "password_one", length = 100)
    private String passwordOne;

    @Column(name = "password_two", length = 100)
    private String passwordTwo;

    @Column(name = "password_three", length = 100)
    private String passwordThree;

    public UserPassword() {
    }

    public UserPassword(String passwordOne, String passwordTwo, String passwordThree) {
        this.passwordOne = passwordOne;
        this.passwordTwo = passwordTwo;
        this.passwordThree = passwordThree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPasswordTwo() {
        return passwordTwo;
    }

    public void setPasswordTwo(String passwordTwo) {
        this.passwordTwo = passwordTwo;
    }

    public String getPasswordThree() {
        return passwordThree;
    }

    public void setPasswordThree(String passwordThree) {
        this.passwordThree = passwordThree;
    }

    public String getPasswordOne() {
        return passwordOne;
    }

    public void setPasswordOne(String passwordOne) {
        this.passwordOne = passwordOne;
    }

    public UserPassword(int id, User user, String passwordTwo, String passwordThree, String passwordOne) {
        this.id = id;
        this.user = user;
        this.passwordTwo = passwordTwo;
        this.passwordThree = passwordThree;
        this.passwordOne = passwordOne;
    }

    @Override
    public String toString() {
        return "UserPassword{" +
                "id=" + id +
                ", user=" + user +
                ", passwordOne='" + passwordOne + '\'' +
                ", passwordTwo='" + passwordTwo + '\'' +
                ", passwordThree='" + passwordThree + '\'' +
                '}';
    }
}

package com.practice.Assignment.repo.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.Assignment.entities.store.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}

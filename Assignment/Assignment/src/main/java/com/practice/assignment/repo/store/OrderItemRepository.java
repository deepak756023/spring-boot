package com.practice.assignment.repo.store;

import com.practice.assignment.entities.store.OrderItem;
import com.practice.assignment.entities.store.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}

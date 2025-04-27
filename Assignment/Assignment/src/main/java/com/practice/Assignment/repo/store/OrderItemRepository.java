package com.practice.Assignment.repo.store;

import com.practice.Assignment.entities.store.OrderItem;
import com.practice.Assignment.entities.store.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}

package com.practice.Assignment.service;

import com.practice.Assignment.entities.store.OrderItem;
import com.practice.Assignment.entities.store.OrderItemId;
import com.practice.Assignment.repo.store.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> getOrderItem(int orderId, int productId) {
        OrderItemId id = new OrderItemId(orderId, productId);
        return orderItemRepository.findById(id);
    }
}

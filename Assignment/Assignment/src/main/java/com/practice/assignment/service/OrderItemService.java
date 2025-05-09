package com.practice.assignment.service;

import com.practice.assignment.entities.store.OrderItem;
import com.practice.assignment.entities.store.OrderItemId;
import com.practice.assignment.repo.store.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> getOrderItem(int orderId, int productId) {
        OrderItemId id = new OrderItemId(orderId, productId);
        return orderItemRepository.findById(id);
    }
}

package com.practice.Assignment.controller;

import com.practice.Assignment.entities.store.OrderItem;
import com.practice.Assignment.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/orderItems")
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/orders/{orderId}/{productId}")
    public Optional<OrderItem> getOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer productId
    ) {
        return orderItemService.getOrderItem(orderId, productId);
    }
}

package com.practice.assignment.controller;

import com.practice.assignment.entities.store.OrderItem;
import com.practice.assignment.service.OrderItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class OrderItemController {


    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

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

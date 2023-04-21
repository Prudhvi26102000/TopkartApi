package com.example.topkart.service;

import com.example.topkart.dto.OrderDto;
import com.example.topkart.dto.OrderRequest;
import com.example.topkart.model.Order;

public interface OrderService {

    OrderDto placeOrder(OrderRequest orderRequest);
    OrderDto getOrderById(Long id);
    void approveOrder(Long orderId);
}

package com.example.topkart.controller;


import com.example.topkart.dto.LightningDealDto;
import com.example.topkart.dto.OrderDto;
import com.example.topkart.dto.OrderRequest;
import com.example.topkart.model.Order;
import com.example.topkart.service.LightningDealService;
import com.example.topkart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private LightningDealService lightningDealService;
    @Autowired
    private OrderService orderService;
    @GetMapping("/lightning-deals")
    public ResponseEntity<List<LightningDealDto>> getUnexpiredLightningDeals() {
        List<LightningDealDto> lightningDealDtos=this.lightningDealService.getUnexpiredLightningDeals();
        return new ResponseEntity<>(lightningDealDtos, HttpStatus.OK);
    }
    @GetMapping("/lightning-deals/{dealId}")
    public ResponseEntity<LightningDealDto> getLightningDealById(@PathVariable Long dealId) {
        LightningDealDto lightningDealDto=this.lightningDealService.getLightningDealById(dealId);
        return new ResponseEntity<>(lightningDealDto,HttpStatus.OK);
    }
    @PostMapping("/orders")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody OrderRequest orderRequest)
    {
        OrderDto order=this.orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
}


package com.example.topkart.controller;


import com.example.topkart.dto.ApiResponse;
import com.example.topkart.dto.LightningDealDto;
import com.example.topkart.service.LightningDealService;
import com.example.topkart.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private LightningDealService lightningDealService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/lightning-deals")
    public ResponseEntity<LightningDealDto> createLightningDeal(@RequestBody @Valid LightningDealDto lightningDealDto) {
        LightningDealDto savedLightningDeal = lightningDealService.createDeal(lightningDealDto);
        return new ResponseEntity<>(savedLightningDeal, HttpStatus.CREATED);
    }

    @PutMapping("/lightning-deals/{id}")
    public ResponseEntity<LightningDealDto> updateLightningDeal(@PathVariable Long id, @RequestBody @Valid LightningDealDto lightningDealDto) {
        LightningDealDto updatedLightningDeal = lightningDealService.updateLightningDeal(id, lightningDealDto);
        return new ResponseEntity<>(updatedLightningDeal,HttpStatus.OK);
    }

    @PostMapping("/{orderId}/approve")
    public ResponseEntity<ApiResponse> approveOrder(@PathVariable Long orderId) {
        // Check if the order exists
        orderService.approveOrder(orderId);
        return new ResponseEntity<>(new ApiResponse("Order is Approved!!",true),HttpStatus.OK);
    }


}

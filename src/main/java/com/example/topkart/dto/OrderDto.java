package com.example.topkart.dto;

import com.example.topkart.model.LightningDeal;
import com.example.topkart.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private long orderId;
    private String username;
    private String productname;
    private LocalDateTime orderTime;
    private String status;
    private double totalprice;
    private int quantity;
    private UserDto user;
    private LightningDealDto deal;

}

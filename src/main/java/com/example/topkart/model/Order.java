package com.example.topkart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Order_Table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @ManyToOne
    private User user;

    @ManyToOne
    private LightningDeal deal;

    private String username;
    private String productname;
    private LocalDateTime orderTime;
    private String status;
    private double totalprice;
    private int quantity;
}

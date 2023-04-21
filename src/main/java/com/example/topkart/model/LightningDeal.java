package com.example.topkart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="lightningdeals")
public class LightningDeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deal_id;
    private String productName;
    private double actualPrice;
    private double finalPrice;
    private int totalUnits;
    private int availableUnits;
    private LocalDateTime expiryTime;

    @OneToMany(mappedBy = "deal",cascade = CascadeType.ALL)
    private List<Order> orders=new ArrayList<>();

    public boolean expired() {
        return LocalDateTime.now().isAfter(this.expiryTime);
    }

}

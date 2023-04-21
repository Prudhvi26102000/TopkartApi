package com.example.topkart.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.convert.DataSizeUnit;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LightningDealDto {

    private Long deal_id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Double actualPrice;

    @Column(nullable = false)
    private Double finalPrice;

    @Column(nullable = false)
    private Integer totalUnits;

    @Column(nullable = false)
    private Integer availableUnits;

    @Column(nullable = false)
    private LocalDateTime expiryTime;
}

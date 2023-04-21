package com.example.topkart.repository;

import com.example.topkart.model.LightningDeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface LightningDealRepo extends JpaRepository<LightningDeal,Long> {

    boolean existsByProductName(String productName);
    //boolean existsByProductNameAndIdNot(String productName, Long id);
    List<LightningDeal> findByExpiryTimeGreaterThan(LocalDateTime expiryTime);
}

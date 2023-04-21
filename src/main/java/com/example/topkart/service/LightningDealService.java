package com.example.topkart.service;

import com.example.topkart.dto.ApiResponse;
import com.example.topkart.dto.LightningDealDto;
import com.example.topkart.model.LightningDeal;

import java.util.List;

public interface LightningDealService {

    LightningDealDto createDeal(LightningDealDto lightningDealDto);
    LightningDealDto updateLightningDeal(Long id, LightningDealDto lightningDealDto);
    List<LightningDealDto> getUnexpiredLightningDeals();
    LightningDealDto getLightningDealById(Long dealId);
}

package com.example.topkart.service.impl;

import com.example.topkart.dto.ApiResponse;
import com.example.topkart.dto.LightningDealDto;
import com.example.topkart.dto.UserDto;
import com.example.topkart.exception.ApiException;
import com.example.topkart.exception.ResourceNotFoundException;
import com.example.topkart.model.LightningDeal;
import com.example.topkart.repository.LightningDealRepo;
import com.example.topkart.service.LightningDealService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LightningDealServiceImpl implements LightningDealService {

    @Autowired
    private LightningDealRepo lightningDealRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public LightningDealDto createDeal(LightningDealDto lightningDealDto) {

        //Check if product is already exists
        if (this.lightningDealRepo.existsByProductName(lightningDealDto.getProductName())) {
            throw new ApiException("Product already exists!!");
        }

        //Validate the input data
        if (lightningDealDto.getActualPrice() < 0 || lightningDealDto.getFinalPrice() < 0 ||
                lightningDealDto.getTotalUnits() <= 0 || lightningDealDto.getAvailableUnits() <= 0 ||
                lightningDealDto.getAvailableUnits() > lightningDealDto.getTotalUnits() ||
                lightningDealDto.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new ApiException("Product Expired to buy!!");
        }

        LightningDeal lightningDeal=this.modelMapper.map(lightningDealDto,LightningDeal.class);
        LightningDeal savedLightningDeal = this.lightningDealRepo.save(lightningDeal);

        return this.modelMapper.map(savedLightningDeal,LightningDealDto.class);
    }

    @Override
    public LightningDealDto updateLightningDeal(Long id, LightningDealDto lightningDealDto) {
        // Check if the deal exists
        Optional<LightningDeal> optionalLightningDeal = this.lightningDealRepo.findById(id);
        if (!optionalLightningDeal.isPresent()) {
            throw new ApiException("LightningDeal is Not Found");
        }

        //Validate the input data
        if (lightningDealDto.getActualPrice() < 0 || lightningDealDto.getFinalPrice() < 0 ||
                lightningDealDto.getTotalUnits() <= 0 || lightningDealDto.getAvailableUnits() <= 0 ||
                lightningDealDto.getAvailableUnits() > lightningDealDto.getTotalUnits() ||
                lightningDealDto.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new ApiException("Product Expired to buy!!");
        }

        // Update the deal in the database
        LightningDeal existingLightningDeal = optionalLightningDeal.get();
        if(lightningDealDto.getProductName()!=null)
            existingLightningDeal.setProductName(lightningDealDto.getProductName());
        if(lightningDealDto.getActualPrice()!=null)
            existingLightningDeal.setActualPrice(lightningDealDto.getActualPrice());
        if(lightningDealDto.getFinalPrice()!=null)
            existingLightningDeal.setFinalPrice(lightningDealDto.getFinalPrice());
        if(lightningDealDto.getTotalUnits()!=null)
            existingLightningDeal.setTotalUnits(lightningDealDto.getTotalUnits());
        if(lightningDealDto.getAvailableUnits()!=null)
            existingLightningDeal.setAvailableUnits(lightningDealDto.getAvailableUnits());
        if(lightningDealDto.getExpiryTime()!=null)
            existingLightningDeal.setExpiryTime(lightningDealDto.getExpiryTime());

        LightningDeal updatedLightningDeal = this.lightningDealRepo.save(existingLightningDeal);

        return this.modelMapper.map(updatedLightningDeal,LightningDealDto.class);
    }

    @Override
    public List<LightningDealDto> getUnexpiredLightningDeals() {
        LocalDateTime currentDate= LocalDateTime.now();

        System.out.println(currentDate);
        List<LightningDeal> unexpiredDeals = this.lightningDealRepo.findByExpiryTimeGreaterThan(currentDate);


        List<LightningDealDto> lightningDealDtos=unexpiredDeals.stream().map(lightningDeal->this.modelMapper.map(lightningDeal,LightningDealDto.class)).collect(Collectors.toList());
        return lightningDealDtos;
    }

    @Override
    public LightningDealDto getLightningDealById(Long dealId) {
        LightningDeal lightningDeal=this.lightningDealRepo.findById(dealId)
                .orElseThrow(()->new ResourceNotFoundException("LightningDeal","dealId",dealId));
        LightningDealDto lightningDealDto=this.modelMapper.map(lightningDeal,LightningDealDto.class);
        return lightningDealDto;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void refreshLightningDeals() {
        List<LightningDeal> deals = lightningDealRepo.findAll();
        for (LightningDeal deal : deals) {
            deal.setExpiryTime(LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0));
            lightningDealRepo.save(deal);
        }
    }

}

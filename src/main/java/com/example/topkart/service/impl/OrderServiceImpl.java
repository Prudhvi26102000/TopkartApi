package com.example.topkart.service.impl;

import com.example.topkart.dto.OrderDto;
import com.example.topkart.dto.OrderRequest;
import com.example.topkart.exception.ApiException;
import com.example.topkart.exception.ResourceNotFoundException;
import com.example.topkart.model.LightningDeal;
import com.example.topkart.model.Order;
import com.example.topkart.model.User;
import com.example.topkart.repository.LightningDealRepo;
import com.example.topkart.repository.OrderRepo;
import com.example.topkart.repository.UserRepo;
import com.example.topkart.service.OrderService;
import com.example.topkart.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LightningDealRepo lightningDealRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public OrderDto placeOrder(OrderRequest orderRequest) {

        //check if the lightning deal exists..
        LightningDeal lightningDeal=lightningDealRepo.findById(orderRequest.getDealId())
                .orElseThrow(()->new ResourceNotFoundException("Deal","DealId",orderRequest.getDealId()));
        User user = userRepo.findById(orderRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id",orderRequest.getUserId()));

        if (orderRequest.getQuantity()<=0) {
            throw new ApiException("Invalid order data");
        }

        if (lightningDeal.getAvailableUnits() <= 0 || LocalDateTime.now().isAfter(lightningDeal.getExpiryTime())) {
            throw new ApiException("Lightning deal is no longer available");
        }

        if(lightningDeal.getAvailableUnits() < orderRequest.getQuantity()){
            throw new ApiException("Not enough Quantity Available");
        }

        if (lightningDeal.getExpiryTime().isAfter(LocalDateTime.now().plusHours(12))) {
            System.out.println(lightningDeal.getExpiryTime());
            System.out.println(LocalDateTime.now().plusHours(12));
            throw new ApiException("Lightning deal has expired");
        }

        //calculate total price for chosen quantity..
        double totalPrice=orderRequest.getQuantity()*lightningDeal.getFinalPrice();

        lightningDeal.setAvailableUnits(lightningDeal.getAvailableUnits() - orderRequest.getQuantity());
        this.lightningDealRepo.save(lightningDeal);

        Order order=new Order();
        order.setDeal(lightningDeal);
        order.setUser(user);
        order.setStatus("pending");
        order.setTotalprice(totalPrice);
        order.setQuantity(orderRequest.getQuantity());
        order.setOrderTime(LocalDateTime.now());
        order.setUsername(user.getName());
        order.setProductname(lightningDeal.getProductName());

        Order saveOrder=orderRepo.save(order);
        return this.modelMapper.map(saveOrder, OrderDto.class);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order=this.orderRepo.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Order","OrderId",id));
            // check if the corresponding lightning deal has expired
            LightningDeal deal = order.getDeal();
            LocalDateTime expiryTime = deal.getExpiryTime();
            LocalDateTime currentTime = LocalDateTime.now(ZoneOffset.UTC);
            if (currentTime.isAfter(expiryTime)) {
                // update order status to "expired"
                order.setStatus("expired");
                orderRepo.save(order);
            }
            return this.modelMapper.map(order,OrderDto.class);

    }

    @Override
    public void approveOrder(Long orderId){
        // Check if the order exists
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order","OrderId",orderId));

        // Check if the corresponding lightning deal exists and is still available
        LightningDeal lightningDeal = lightningDealRepo.findById(order.getDeal().getDeal_id())
                .orElseThrow(() -> new ResourceNotFoundException("LightningDeal","lightningDealId",order.getDeal().getDeal_id()));

        if (lightningDeal.expired()) {
                throw new ApiException("LightningDeal is Expired");
            }

        if(order.getStatus()=="approved")
            throw new ApiException("Order is already Approved!!");

        // Update the order status to "approved"
        order.setStatus("approved");
        orderRepo.save(order);
    }

}

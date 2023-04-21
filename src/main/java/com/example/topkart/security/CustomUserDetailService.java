package com.example.topkart.security;

import com.example.topkart.exception.ResourceNotFoundException;
import com.example.topkart.model.User;
import com.example.topkart.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //loading user from database by username
        User user=this.userepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","email "+username,0));

        return user;
    }

}
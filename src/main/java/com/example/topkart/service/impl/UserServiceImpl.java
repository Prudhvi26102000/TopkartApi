package com.example.topkart.service.impl;

import com.example.topkart.config.AppConstants;
import com.example.topkart.dto.UserDto;
import com.example.topkart.exception.ResourceNotFoundException;
import com.example.topkart.model.Role;
import com.example.topkart.model.User;
import com.example.topkart.repository.RoleRepo;
import com.example.topkart.repository.UserRepo;
import com.example.topkart.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userrepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        //Encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        //roles
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);
        User newUser = this.userrepo.save(user);
        return this.modelMapper.map(newUser,UserDto.class);
    }


    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user=this.userrepo.findById(userId)
                .orElseThrow(() ->new ResourceNotFoundException("User","id",userId));

        if(userDto.getName()!=null)
            user.setName(userDto.getName());
        if(userDto.getEmail()!=null)
            user.setEmail(userDto.getEmail());
        if(userDto.getPassword()!=null)
            user.setPassword(userDto.getPassword());

        User updateduser=this.userrepo.save(user);
        UserDto userDto1=this.modelMapper.map(updateduser,UserDto.class);

        return userDto1;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user=this.userrepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));

        UserDto userDto=this.modelMapper.map(user,UserDto.class);

        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=this.userrepo.findAll();

        List<UserDto> userDtos=users.stream().map(user->this.modelMapper.map(user,UserDto.class)).collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public void deleteUser(Long userId) {
        User user=this.userrepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        this.userrepo.delete(user);
    }
}

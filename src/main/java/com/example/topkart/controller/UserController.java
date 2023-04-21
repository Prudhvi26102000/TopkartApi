package com.example.topkart.controller;

import com.example.topkart.dto.ApiResponse;
import com.example.topkart.dto.UserDto;
import com.example.topkart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //GetAllusers
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        List<UserDto> allusers=this.userService.getAllUsers();
        return new ResponseEntity<>(allusers,HttpStatus.OK);
    }

    //Update User
    @PutMapping("/updateuser/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Long userId)
    {
        UserDto updatedUser=this.userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    //Delete User
    @DeleteMapping("/deleteuser/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId)
    {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
    }

    //Get Single User
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Long userId)
    {
        UserDto singleUser=this.userService.getUserById(userId);
        return new ResponseEntity<>(singleUser,HttpStatus.OK);
    }
}
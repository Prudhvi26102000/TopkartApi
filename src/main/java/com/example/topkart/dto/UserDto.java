package com.example.topkart.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.*;

@Data
public class UserDto {

    private Long id;
    @NotEmpty(message="Username must not be empty" )
    @Size(min=4,message="Username must be minimum of 4 characters !! ")
    private String name;
    @Email(message="Email is not valid !! ")
    private String email;

    @NotEmpty(message="Password must not be empty" )
    @Size(min=3,max=14,message="Password must be min of 3 chars and max of 14 chars !!")
    @JsonIgnore
    private String password;
    private Set<RoleDto> roles=new HashSet<>();
//    private List<Order> orders;

}

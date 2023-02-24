package com.f89497.CSCB025_LogisticCompany.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDTO implements Serializable{
    @NotNull
    @NotEmpty(message = "Username can not be empty")
    String username;
    
    @NotNull
    @NotEmpty(message = "Password can not be empty")
    String password;

    @NotNull
    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Please provide a valid email")
    String email;
}

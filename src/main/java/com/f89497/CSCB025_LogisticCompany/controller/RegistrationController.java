package com.f89497.CSCB025_LogisticCompany.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.f89497.CSCB025_LogisticCompany.dto.UserDTO;
import com.f89497.CSCB025_LogisticCompany.exception.UserAlreadyExistException;
import com.f89497.CSCB025_LogisticCompany.service.UserDetailsServiceImpl;

@Controller
public class RegistrationController {

    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/register")
    public String register(final Model model){
        model.addAttribute("user", new UserDTO());
        return "registration";
    }

    @PostMapping("/register")
    public String userRegistration(final @Valid  UserDTO userDTO, final BindingResult bindingResult, final Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("registrationForm", userDTO);
            return "registration";
        }
        try {
            userService.register(userDTO);
        }catch (UserAlreadyExistException e){
            bindingResult.rejectValue("email", "userDTO.email","An account already exists for this email.");
            model.addAttribute("registrationForm", userDTO);
            return "registration";
        }
        return "redirect:/login";
    }
}

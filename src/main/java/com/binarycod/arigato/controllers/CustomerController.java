package com.binarycod.arigato.controllers;

import com.binarycod.arigato.domain.CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping
    public String getCustomerHome(){
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        model.addAttribute("customUser", customUser);
        return "user_register";
    }

}

package com.binarycod.arigato.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome(Authentication authentication){
        User loggedInUser = (User) authentication.getPrincipal();
        Collection<GrantedAuthority> authorities = loggedInUser.getAuthorities();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");

        if (authorities.contains(authority))
            System.out.println("User is admin");
        else
            System.out.println("User is ordinary User");
        return "home";
    }

}

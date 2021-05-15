package com.binarycod.arigato.controllers;

import com.binarycod.arigato.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "home";
    }

}

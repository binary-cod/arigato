package com.binarycod.arigato.controllers;

import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "home";
    }

    @GetMapping("/public/product/details")
    public String showProductDetails(@RequestParam Long id, Model model){
        Optional<Product> optionalProduct = productService.getProductById(id);
        if (!optionalProduct.isPresent())
            return "redirect:/";

        model.addAttribute("product", optionalProduct.get());
        return "product_details";
    }

}

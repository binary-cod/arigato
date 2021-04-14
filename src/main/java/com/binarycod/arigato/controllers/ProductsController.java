package com.binarycod.arigato.controllers;

import com.binarycod.arigato.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.ArrayList;


@Controller
@RequestMapping("/products")
public class ProductsController {

    List<Product> productList = new ArrayList<>();

    @GetMapping
    public String getProducts(Model model){
        model.addAttribute("products", productList);
        model.addAttribute("product", new Product());
        return "product_list";
    }

    @PostMapping
    public String createProduct(@RequestParam Long pid, @RequestParam String pname, @RequestParam Double price, @RequestParam Integer size){
        System.out.println("I am handling post to this endpoint!");
        System.out.println(pid +"  "+ pname + "  "+price);
        productList.add(new Product(pid, pname, price));
        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long id){

        return "redirect:/products";
    }

}

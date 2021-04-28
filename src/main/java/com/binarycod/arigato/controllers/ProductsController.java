package com.binarycod.arigato.controllers;

import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
@RequestMapping("/products")
public class ProductsController {

    private String error="";

    @Autowired
    ProductService productService;

    @GetMapping
    public String getProducts(Model model){

        Integer numberOfProducts = productService.getCount();

        model.addAttribute("error", error);
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("productCount", numberOfProducts);

        return "product_list";
    }

    @GetMapping("/new")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "new_product";
    }

    @PostMapping
    public String createProduct(Product product){
        productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long id){
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String editProduct(@RequestParam Long id, Model model){

        Optional<Product> productOptional = productService.getProduct(id);

        if (!productOptional.isPresent()) {
            error = "No such product in out store!";
            return "redirect:/products";
        }
        model.addAttribute("product", productOptional.get());

        return "edit_product";
    }

}

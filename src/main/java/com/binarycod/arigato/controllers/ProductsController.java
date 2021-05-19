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
@RequestMapping("/admin/products")
public class ProductsController {

    @Autowired
    ProductService productService;

    @GetMapping
    public String getProducts(Model model){
       model.addAttribute("products", productService.getAllProducts());
       return "product_list";
    }

    @GetMapping("/new")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "new_product";

    }

    @PostMapping
    public String createProduct(@RequestParam Long id, @RequestParam String name,
                                @RequestParam Double price, @RequestParam Integer size,
                                @RequestParam String imageUrl){
        Product p = new Product (id, name, price, size, imageUrl);
        productService.createOrUpdateProduct(p);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long id){

        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit")
    public String editProduct(@RequestParam Long id, Model model){

        Optional<Product> productOptional = productService.getProductById(id);
        if (!productOptional.isPresent())
            return "redirect:/admin/products";

        model.addAttribute("product", productOptional.get());
        return "edit_product";
    }

    @PostMapping("/edit")
    public String saveProduct(Product product){

        productService.createOrUpdateProduct(product);
        return "redirect:/admin/products";
    }
}

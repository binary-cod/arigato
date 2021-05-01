package com.binarycod.arigato.controllers;

import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.domain.StoreProduct;
import com.binarycod.arigato.services.ProductService;
import com.binarycod.arigato.services.StoreManagementService;
import com.binarycod.arigato.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/manage_store")
public class StoreManagementController {

    @Autowired
    ProductService productService;

    @Autowired
    StoreService storeService;

    @Autowired
    StoreManagementService storeManagementService;

    @GetMapping("/accept_product")
    public String showAcceptProduct(@RequestParam Long productId, Model model){
        Optional<Product> product = productService.getProduct(productId);
        if (!product.isPresent())
            return "redirect:/products";

        model.addAttribute("product", product.get());
        model.addAttribute("stores", storeService.getStoreList());
        model.addAttribute("storeProduct", new StoreProduct());
        return "accept_store_product";
    }

    @PostMapping("/accept_product")
    public String acceptProductToStore(@RequestParam Long productId,
                                       @RequestParam Integer storeId,
                                       @RequestParam Integer quantity ){

        storeManagementService.putProductToStore(storeId, productId, quantity);
        return "redirect:/products";
    }

    @GetMapping("/list_of_products")
    public String showListOfProductsInStore(Model model){
        model.addAttribute("productInStore", storeManagementService.getProductsInStore());
        return "store_products_list";
    }

}

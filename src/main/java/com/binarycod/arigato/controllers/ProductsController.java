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


@Controller
@RequestMapping("/products")
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
    public String createProduct(@RequestParam Long id, @RequestParam String name, @RequestParam Double price, @RequestParam Integer size){
    //    productList.add(product);
        Product p = new Product (id, name, price, size);
        productService.createProduct(p);
        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long id){
    /*    productList = productList
                .stream()
                .filter(p -> !p.getId().equals(id))
                .collect(Collectors.toList());*/

        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String editProduct(@RequestParam Long id, Model model){
     /*   Optional<Product> productOptional = productList
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (!productOptional.isPresent())
            return "redirect:/products";

        model.addAttribute("product", productOptional.get());*/

        return "edit_product";
    }

    @PostMapping("/edit")
    public String saveProduct(Product product){
     /*   Optional<Product> productOld = productList
                .stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst();

        if (productOld.isPresent()){
            productList.remove(productOld.get());
            productList.add(product);
        }*/
        /*
        for (Product p: productList) {
            if (p.getId().equals(product.getId()))
                p.setName(product.getName());
                p.setPrice(product.getPrice());
                p.setSize(product.getSize());
        }*/

        return "redirect:/products";
    }
}

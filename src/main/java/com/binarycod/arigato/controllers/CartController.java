package com.binarycod.arigato.controllers;

import com.binarycod.arigato.domain.Cart;
import com.binarycod.arigato.domain.CartItem;
import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
@SessionAttributes("cart")
public class CartController {

    @Autowired
    ProductService productService;

    @GetMapping
    public String getCart(@ModelAttribute("cart") Cart cart){
        return "redirect:/";
    }

    @GetMapping("/add/{id}")
    public String addItemToCart(@PathVariable Long id, @ModelAttribute("cart") Cart cart){
        Optional<Product> optionalProduct = productService.getProductById(id);

        if (!optionalProduct.isPresent())
            return "redirect:/";

        Product p = optionalProduct.get();
        CartItem cartItem = new CartItem(p, 1, p.getPrice() * 1);

        if (cart.getUuid() == null) {
            cart.setUuid(UUID.randomUUID());
            cart.getCartItemList().add(cartItem);
        } else {
            cart.getCartItemList().add(cartItem);
        }

        return "redirect:/";
    }

    @GetMapping("/checkout")
    public String checkoutItems(@ModelAttribute("cart") Cart cart){
        cart.getCartItemList().forEach(ci -> System.out.println(ci.getProduct().getName()+" "+ci.getTotalPrice()));
        return "redirect:/";
    }

    @GetMapping("/details")
    public String showDetails(@ModelAttribute("cart") Cart cart, Model model){

        model.addAttribute("cartItems", cart.getGroupedItems());
        return "cart_details";
    }

    @ModelAttribute("cart")
    public Cart cart(){
        return new Cart();
    }
}

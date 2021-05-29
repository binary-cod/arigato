package com.binarycod.arigato.controllers;

import com.binarycod.arigato.domain.Cart;
import com.binarycod.arigato.domain.CartItem;
import com.binarycod.arigato.domain.CustomUser;
import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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


    @GetMapping("/details")
    public String showDetails(@ModelAttribute("cart") Cart cart, Model model){
        List<CartItem> cartItems = cart.getGroupedItems();
        if (cartItems.size() == 0)
            return "redirect:/";

        Optional<Double> totalPrice = cartItems
                .stream()
                .map(cartItem -> cartItem.getTotalPrice())
                .reduce((aDouble, aDouble2) -> (aDouble + aDouble2));
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice.get());
        return "cart_details";
    }

    @GetMapping("/items/delete/{id}")
    public String deleteItemFromCard(@PathVariable String id, @ModelAttribute("cart") Cart cart){
        cart.removeItem(UUID.fromString(id));
        return "redirect:/cart/details";
    }

    @GetMapping("/checkout")
    public String cartCheckout(Authentication authentication, @ModelAttribute("cart") Cart cart){
        if (authentication.isAuthenticated())
            cart.setOwner((CustomUser) authentication.getPrincipal());
        return "redirect:/cart/details";
    }

    @ModelAttribute("cart")
    public Cart cart(){
        return new Cart();
    }
}

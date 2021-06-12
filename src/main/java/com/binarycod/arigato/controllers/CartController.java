package com.binarycod.arigato.controllers;

import com.binarycod.arigato.domain.Cart;
import com.binarycod.arigato.domain.CartItem;
import com.binarycod.arigato.domain.CustomUser;
import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.services.ProductService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Value("${stripe.keys.public}")
    private String stripePublicKey;

    @Value("${stripe.keys.secret}")
    private String stripeSecretKey;

    PaymentIntent intent;
    private Gson gson = new Gson();

    @GetMapping
    public String getCart(@ModelAttribute("cart") Cart cart) {
        return "redirect:/";
    }

    @GetMapping("/add/{id}")
    public String addItemToCart(@PathVariable Long id, @ModelAttribute("cart") Cart cart) {
        //1. checking if product exists, if not redirects to "/"
        Optional<Product> optionalProduct = productService.getProductById(id);
        if (!optionalProduct.isPresent())
            return "redirect:/";
        //2. checking if cart exist?
        if (cart.getUuid() == null) {
            cart.setUuid(UUID.randomUUID());
        }
        //3. checking if cartItem with same product exist in cart, in order not to duplicate
        boolean match = false;
        for (CartItem cartItem : cart.getCartItemList()) {
            if (cartItem.getProduct().getId() == id) {
                match = true;
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItem.setTotalPrice(cartItem.getTotalPrice() + cartItem.getProduct().getPrice());
            }
        }
        if (!match) {
            Product p = optionalProduct.get();
            CartItem cartItem = new CartItem(p, 1, p.getPrice() * 1);
            cart.getCartItemList().add(cartItem);
        }

        return "redirect:/";
    }


    @GetMapping("/details")
    public String showDetails(@ModelAttribute("cart") Cart cart, Model model) {
        List<CartItem> cartItems = cart.getCartItemList();
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
    public String deleteItemFromCard(@PathVariable String id, @ModelAttribute("cart") Cart cart) {
        cart.removeItem(UUID.fromString(id));
        return "redirect:/cart/details";
    }

    @GetMapping("/checkout")
    public String cartCheckout(Authentication authentication,
                               @ModelAttribute("cart") Cart cart,
                               Model model) {

        if (authentication.isAuthenticated())
            cart.setOwner((CustomUser) authentication.getPrincipal());
        else
            return "redirect:/login";


        CustomUser owner = (CustomUser) authentication.getPrincipal();
        model.addAttribute("userProfile", owner.getUserProfile());
        model.addAttribute("totalPrice", cart.getCartTotalPrice().get());

        return "checkout_with_stripe";
    }

    @PostMapping("create-payment-intent")
    public ResponseEntity<String> createPaymentIntent(Model model,
                                                      @ModelAttribute("cart") Cart cart,
                                                      Authentication authentication) {
        Stripe.apiKey = stripeSecretKey;

        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("usd")
                .setAmount((long) (cart.getCartTotalPrice().get() * 100))
                .build();

        try {
            intent = PaymentIntent.create(createParams);
        } catch (StripeException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        String json = "{\"clientSecret\":\"" + intent.getClientSecret() + "\"}";
        JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
        return new ResponseEntity<String>(String.valueOf(convertedObject), headers, HttpStatus.OK);
    }

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }
}

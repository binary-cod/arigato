package com.binarycod.arigato.controllers;

import com.binarycod.arigato.domain.Cart;
import com.binarycod.arigato.domain.CustomUser;
import com.binarycod.arigato.domain.Order;
import com.binarycod.arigato.domain.OrderItem;
import com.binarycod.arigato.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;


    @GetMapping("/list")
    public String showOrderList(Model model, Authentication authentication){
        if (!authentication.isAuthenticated())
            return "redirect:/login";
        model.addAttribute("orders", orderService.getAllListOfOrderOfUser((CustomUser)authentication.getPrincipal()));
        return "order_list";
    }

    @GetMapping("/place")
    public String placeAnOrder(@ModelAttribute("cart") Cart cart,
                               Authentication authentication){

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setOwner((CustomUser) authentication.getPrincipal());
        order.setOrderItems(
                cart.getCartItemList()
                        .stream()
                        .map(cartItem -> {
                            OrderItem orderItem = new OrderItem();
                            orderItem.setProduct(cartItem.getProduct());
                            orderItem.setQuantity(cartItem.getQuantity());
                            orderItem.setTotalPrice(cartItem.getQuantity() * cartItem.getProduct().getPrice());
                            return orderItem;
                        }).collect(Collectors.toList())

        );
        order.setStatus(Order.STATUS.NEW);

        orderService.placeAnOrder(order);
        //TODO- empt cart Items
        return "redirect:/order/list";
    }

}

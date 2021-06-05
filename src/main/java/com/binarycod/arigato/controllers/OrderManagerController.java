package com.binarycod.arigato.controllers;

import com.binarycod.arigato.domain.Authority;
import com.binarycod.arigato.domain.CustomUser;
import com.binarycod.arigato.domain.Order;
import com.binarycod.arigato.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/admin/orders")
public class OrderManagerController {

    @Autowired
    OrderService orderService;

    @GetMapping("/list/{status}")
    public String showOrderList(@PathVariable String status, Model model, Authentication authentication) {
        if (!authentication.isAuthenticated())
            return "redirect:/login";

        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Optional<Authority> optionalAdminAuthority = customUser
                .getAuthorities()
                .stream()
                .filter(authority -> authority.getAuthority().equals("ADMIN"))
                .findFirst();

        if (!optionalAdminAuthority.isPresent()){
            return "redirect:/login";
        }

        model.addAttribute("orders", orderService.getAllListOfOrderByStatus(Order.STATUS.valueOf(status)));
        return "admin_order_list";
    }
}

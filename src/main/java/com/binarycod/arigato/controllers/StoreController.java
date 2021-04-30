package com.binarycod.arigato.controllers;

import com.binarycod.arigato.domain.Store;
import com.binarycod.arigato.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    StoreService storeService;

    @GetMapping
    public String newStore(Model model){
        model.addAttribute("store", new Store());
        return "new_store";
    }

    @PostMapping
    public String createStore(Store store){
        storeService.createNewStore(store);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String getStoreById(@RequestParam Integer id){
        System.out.println(storeService.getStore(id));
        return "redirect:/";
    }
}

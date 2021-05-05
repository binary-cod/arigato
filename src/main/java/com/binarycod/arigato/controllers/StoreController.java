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
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    StoreService storeService;

    @GetMapping("/list")
    public String listStores(Model model){

     //   model.addAttribute("stores", storeService.getStoreList());
        return "store_list";
    }

    @GetMapping
    public String newStore(Model model){
        model.addAttribute("store", new Store());
        return "new_store";
    }

    @PostMapping
    public String createStore(Store store){
        storeService.createStore(store);
        return "redirect:/stores/list";
    }

    @GetMapping("/edit")
    public String getStoreById(@RequestParam Integer id){
      //  System.out.println(storeService.getStore(id));
        return "redirect:/stores/list";
    }
}
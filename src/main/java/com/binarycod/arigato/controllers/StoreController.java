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

import java.util.Optional;

@Controller
@RequestMapping("/admin/stores")
public class StoreController {

    @Autowired
    StoreService storeService;

    @GetMapping("/list")
    public String listStores(Model model){

        model.addAttribute("stores", storeService.getStoreList());
        return "store_list";
    }

    @GetMapping
    public String newStore(Model model){
        model.addAttribute("store", new Store());
        model.addAttribute("types", Store.TYPE.values());
        return "new_store";
    }

    @PostMapping
    public String createStore(Store store){
        storeService.createOrUpdateStore(store);
        return "redirect:/admin/stores/list";
    }

    @GetMapping("/edit")
    public String getStoreById(@RequestParam Long id, Model model){
        Optional<Store> optionalStore = storeService.getStoreById(id);

        if (!optionalStore.isPresent())
            return "redirect:/admin/stores/list";

        model.addAttribute("store", optionalStore.get());
        return "new_store";
    }

    @GetMapping("/delete")
    public String deleteStore(@RequestParam Integer id) {
        storeService.deleteStore(id);
        return "redirect:/admin/stores/list";
    }
}
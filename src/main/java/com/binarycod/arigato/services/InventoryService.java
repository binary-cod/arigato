package com.binarycod.arigato.services;

import com.binarycod.arigato.domain.Inventory;
import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.domain.Store;
import com.binarycod.arigato.repositories.InventoryRepository;
import com.binarycod.arigato.repositories.ProductRepository;
import com.binarycod.arigato.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoreRepository storeRepository;

    public void acceptProductToInventory(Long productId, Long storeId, Long quantity){

        //step1: check inventory for the product in store
        //step2: if it not exists then create it
        //step3: if it exists then get it and update the quantity

        Product product = productRepository.findById(productId).get();
        Store store = storeRepository.findById(storeId).get();

        //Step1
        Optional<Inventory> inventoryOptional = inventoryRepository.findInventoryByProductAndStore(product, store);
        //Step2
        Inventory inventory = new Inventory();

        if (!inventoryOptional.isPresent()){
            inventory.setProduct(product);
            inventory.setStore(store);
            inventory.setQuantity(quantity);
        } else {
            //Step3
            Inventory oldInventory = inventoryOptional.get();
            inventory.setId(oldInventory.getId());
            inventory.setStore(oldInventory.getStore());
            inventory.setProduct(oldInventory.getProduct());
            inventory.setQuantity(oldInventory.getQuantity() + quantity);
        }

        inventoryRepository.save(inventory);
    }

    public List<Inventory> getProductList(Long storeId) {
        return inventoryRepository.getInventoryByStoreId(storeId);
    }

    public List<Product> getProductsListOfGivenStore(Store.TYPE storeType){
        return inventoryRepository.getInventoryByStoreStoreType(storeType)
                .stream()
                .filter(inventory -> inventory.getQuantity() > 0)
                .map(inventory -> inventory.getProduct())
                .collect(Collectors.toList());
    }

}

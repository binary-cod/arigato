package com.binarycod.arigato.services;

import com.binarycod.arigato.domain.StoreProduct;
import com.binarycod.arigato.repository.StoreManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreManagementService {

    @Autowired
    StoreManagementRepository storeManagementRepository;

    public void putProductToStore(Integer storeId, Long productId, Integer quantity){
        storeManagementRepository.putProductToStore(storeId, productId, quantity);
    }

    public List<StoreProduct> getProductsInStore(){
        return storeManagementRepository.getAllProductsOnStore();
    }
}

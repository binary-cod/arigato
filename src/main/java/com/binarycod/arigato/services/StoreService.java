package com.binarycod.arigato.services;

import com.binarycod.arigato.domain.Store;
import com.binarycod.arigato.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepository;

    public void createStore(Store store){
        storeRepository.save(store);
    }
}

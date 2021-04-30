package com.binarycod.arigato.services;

import com.binarycod.arigato.domain.Store;
import com.binarycod.arigato.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepository;

    public void createNewStore(Store store){
        storeRepository.createStore(store);
    }

    public Optional<Store> getStore(Integer id){
        Store store = storeRepository.getStore(id);
        return (store == null) ? Optional.empty() : Optional.of(store);
    }
}

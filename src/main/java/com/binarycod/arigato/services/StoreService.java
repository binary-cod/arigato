package com.binarycod.arigato.services;

import com.binarycod.arigato.domain.Store;
import com.binarycod.arigato.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepository;

    public void createOrUpdateStore(Store store){

        storeRepository.save(store);
    }

    public List<Store> getStoreList() {
        return storeRepository.findAll();
    }

    public void deleteStore(Integer id) {
        storeRepository.deleteById(id);
    }

    public Optional<Store> getStoreById(Long id) {
        return storeRepository.findById(id);
    }
}

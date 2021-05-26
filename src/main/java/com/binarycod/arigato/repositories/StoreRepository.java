package com.binarycod.arigato.repositories;

import com.binarycod.arigato.domain.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends CrudRepository<Store, Integer> {
    List<Store> findAll();
    Optional<Store> findById(Long id);
}

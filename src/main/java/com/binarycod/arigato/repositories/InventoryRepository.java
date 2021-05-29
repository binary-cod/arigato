package com.binarycod.arigato.repositories;

import com.binarycod.arigato.domain.Inventory;
import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    public List<Inventory> getInventoryByStoreId(Long storeId);

    public Optional<Inventory> findInventoryByProductAndStore(Product product, Store store);

    List<Inventory> getInventoryByStoreStoreType(Store.TYPE storeType);
}

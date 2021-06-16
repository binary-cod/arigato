package com.binarycod.arigato.repositories;

import com.binarycod.arigato.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    List<Product> findAll();

    @Override
    Page<Product> findAll(Pageable pageable);
}

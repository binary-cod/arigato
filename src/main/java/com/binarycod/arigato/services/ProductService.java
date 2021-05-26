package com.binarycod.arigato.services;

import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public void createOrUpdateProduct(Product product){

        productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}

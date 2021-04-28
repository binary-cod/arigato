package com.binarycod.arigato.services;

import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public void createProduct(Product product){
        productRepository.insertProduct(product);
    }

    public List<Product> getProducts(){
        return productRepository.getListOfProducts();
    }

    public Integer getCount(){
        return productRepository.count();
    }

    public Optional<Product> getProduct(Long id) {
        Product p = productRepository.getProductByID(id);

        return ( p != null ) ? Optional.of(p) : Optional.empty();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProduct(id);
    }
}

package com.binarycod.arigato.services;

import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

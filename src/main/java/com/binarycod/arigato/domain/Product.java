package com.binarycod.arigato.domain;

import javax.validation.constraints.NotBlank;

public class Product {

    @NotBlank(message = "Id is required")
    Long id;
    String name;
    Double price;

    public Product(){
    }

    public Product(Long id, String name, Double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }


    public java.lang.Long getId() {
        return id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.Double getPrice() {
        return price;
    }

    public void setPrice(java.lang.Double price) {
        this.price = price;
    }
}

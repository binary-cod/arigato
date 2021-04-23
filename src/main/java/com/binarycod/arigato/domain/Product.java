package com.binarycod.arigato.domain;

public class Product {

    Long id;
    String name;
    Double price;
    Integer size;

    public Product(){
    }

    public Product(Long id, String name, Double price, Integer size){
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}

package com.binarycod.arigato.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;
    Double price;
    Integer size;

    @OneToMany(fetch = FetchType.EAGER)
    List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }



    public Product(){}

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

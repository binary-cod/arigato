package com.binarycod.arigato.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToMany
    @JoinTable(name = "store_product")
    private List<Product> product;

    public Store(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Product> getProduct() {
        return product;
    }
}

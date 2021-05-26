package com.binarycod.arigato.domain;

import java.util.UUID;

public class CartItem {

    private UUID uuid;
    private Product product;
    private Integer quantity;
    private Double totalPrice;

    public CartItem(){
    }
    public CartItem(Product product, Integer quantity, Double totalPrice) {
        this.uuid = UUID.randomUUID();
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

package com.binarycod.arigato.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Cart {

    private UUID uuid;
    private List<CartItem> cartItemList = new ArrayList<>();
    private CustomUser owner;
    private Double totalPrice;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public CustomUser getOwner() {
        return owner;
    }

    public void setOwner(CustomUser owner) {
        this.owner = owner;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean containsItem(UUID uuid){
        Optional<CartItem> item = cartItemList
                .stream()
                .filter(cartItem -> cartItem.getUuid().equals(uuid))
                .findFirst();
        return item.isPresent();
    }

    @Override
    public String toString() {
        return ""+cartItemList.size();

    }

    public void removeItem(UUID uuid) {
        boolean cartItemIsEmpty=false;
        for(CartItem cartItem : cartItemList){
            if(cartItem.getUuid().equals(uuid)){
                cartItem.setQuantity(cartItem.getQuantity()-1);
                cartItem.setTotalPrice(cartItem.getTotalPrice() - cartItem.getProduct().getPrice());
                if(cartItem.getQuantity() <=0){
                    cartItemIsEmpty=true;
                }
            }
        }
        if(cartItemIsEmpty){
            cartItemList=cartItemList.stream()
                    .filter(cartItem -> !cartItem.getUuid().equals(uuid))
                    .collect(Collectors.toList());
        }

    }
}
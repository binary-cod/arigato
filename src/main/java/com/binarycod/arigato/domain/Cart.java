package com.binarycod.arigato.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public void addItem(CartItem cartItem){
        //check if item exists
        // if exists update it
        // else insert it
        if (containsItem(cartItem.getUuid())){
            Optional<CartItem> oldItemOptional = cartItemList.stream()
                    .filter(ci -> ci.getUuid().equals(cartItem.getUuid()))
                    .findFirst();

            if (oldItemOptional.isPresent()) {
                CartItem oldItem = oldItemOptional.get();
                cartItemList.remove(oldItem);
                oldItem.setQuantity(oldItem.getQuantity() + cartItem.getQuantity());
                cartItemList.add(oldItem);
            }

        } else {
            cartItemList.add(cartItem);
        }
    }

    public Boolean containsItem(UUID uuid){
        Optional<CartItem> item = cartItemList
                .stream()
                .filter(cartItem -> cartItem.getUuid().equals(uuid))
                .findFirst();
        return item.isPresent();
    }
}
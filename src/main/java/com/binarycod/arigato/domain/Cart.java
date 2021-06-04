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

    public List<CartItem> getGroupedItems(){
           Map<String, CartItem> groupedMap = new HashMap<>();
           for (CartItem item: cartItemList) {
               if (groupedMap.containsKey(item.getProduct().getName())){
                   CartItem oldItem = groupedMap.get(item.getProduct().getName());
                   oldItem.setQuantity(oldItem.getQuantity() + item.getQuantity());
                   oldItem.setTotalPrice(oldItem.getTotalPrice() + item.getTotalPrice());
                   groupedMap.put(item.getProduct().getName(), oldItem);
               } else {
                 groupedMap.put(item.getProduct().getName(), item);
               }
           }
           return new ArrayList<CartItem>(groupedMap.values());
    }

    public Optional<Double> getCartTotalPrice(){
        return cartItemList
                .stream()
                .map(cartItem -> cartItem.getTotalPrice())
                .reduce((aDouble, aDouble2) -> (aDouble + aDouble2));
    }

    @Override
    public String toString() {
        return ""+cartItemList.size();

    }

    public void removeItem(UUID uuid) {
        cartItemList = cartItemList
                .stream()
                .filter(cartItem -> !cartItem.getUuid().equals(uuid))
                .collect(Collectors.toList());
    }
}
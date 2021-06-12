package com.binarycod.arigato.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="orders")
public class Order {

    public enum STATUS {
        NEW,
        INPROGRESS,
        COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    CustomUser owner;

    LocalDateTime createdAt;

    private STATUS status;

    @OneToMany(cascade = CascadeType.ALL)
    List<OrderItem> orderItems;

    public Order(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomUser getOwner() {
        return owner;
    }

    public void setOwner(CustomUser owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Double orderTotalPrice(){
        return orderItems
                .stream()
                .map(orderItem -> orderItem.totalPrice)
                .reduce((aDouble, aDouble2) -> aDouble + aDouble2).orElse(0.0);
    }
}

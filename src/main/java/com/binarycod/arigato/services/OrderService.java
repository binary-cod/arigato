package com.binarycod.arigato.services;

import com.binarycod.arigato.domain.CustomUser;
import com.binarycod.arigato.domain.Order;
import com.binarycod.arigato.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void placeAnOrder(Order order){
        System.out.println("items "+order.getOrderItems().size());
        orderRepository.save(order);
    }

    public List<Order> getAllListOfOrderOfUser(CustomUser owner){
        return orderRepository.getOrderByOwner(owner);
    }

    public List<Order> getListOfOrderOfUserByStatus(CustomUser owner,
                                                    Order.STATUS status){
        return orderRepository.getOrderByOwnerAndStatus(owner, status);
    }
}

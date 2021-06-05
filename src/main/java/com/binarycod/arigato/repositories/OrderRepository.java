package com.binarycod.arigato.repositories;

import com.binarycod.arigato.domain.CustomUser;
import com.binarycod.arigato.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrderByOwner(CustomUser owner);
    List<Order> getOrderByOwnerAndStatus(CustomUser owner, Order.STATUS status);

    List<Order> getOrderByStatus(Order.STATUS status);
}

package com.ortest.service;

import com.ortest.model.Orders;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Orders> findAll();

    Orders saveOrder(Orders order);

    Optional<Orders> findById(Long id) ;


    void enable(Long id);

    Orders updateOrder(Orders orders, Long id);

    Orders findMaxId();

}

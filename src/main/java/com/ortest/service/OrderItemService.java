package com.ortest.service;

import com.ortest.model.OrderItems;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    OrderItems save(OrderItems orderItem);

    List<OrderItems> findAllItems();

    Optional<OrderItems> findById(Long i);

    List<OrderItems> findOrdersTodelete(Long id);

    void deleteById(Long id);

    public OrderItems updateItem(OrderItems orderItem, Long id);
}

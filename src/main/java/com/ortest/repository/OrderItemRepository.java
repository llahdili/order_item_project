package com.ortest.repository;

import com.ortest.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findAllById(Iterable<Long> longs);

    @Override
    Optional<OrderItems> findById(Long aLong);

    @Query("select count(o) from OrderItems o where o.products.id = ?1")
    Long findRelatedItems(Long id);

    @Query("select o from OrderItems o where o.products.id = ?1")
    List<OrderItems> findOrdersTodelete(Long id);











}
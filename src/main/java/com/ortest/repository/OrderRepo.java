package com.ortest.repository;
import com.ortest.model.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long>{


    List<Orders> findAll();

    @Transactional
    @Modifying
    @Query("update Orders o set o.active = 0 where o.id = :id")
    void desable(Long id);

    @Query(value = "from Orders WHERE id = (SELECT MAX(id) FROM Orders)")
    Orders findMaxId();


}

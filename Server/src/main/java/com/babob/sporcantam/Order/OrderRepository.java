package com.babob.sporcantam.Order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    @Query(
            value = "SELECT * FROM admin u WHERE u.orderID = ?1",nativeQuery = true)
    Collection<Order> findByOrderID(String orderID);


}
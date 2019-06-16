package com.babob.sporcantam.Order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Integer> {
    @Query(
            value = "SELECT * FROM admin u WHERE u.orderID = ?1",nativeQuery = true)
    Collection<Orders> findByOrderID(String orderID);

    @Query(
            value = "SELECT * FROM order o WHERE o.order_id IN :sale_id_list", nativeQuery = true)
    Collection<Orders> findByOrderIDList(@Param("sale_id_list") Collection<String> sale_id_list);
}
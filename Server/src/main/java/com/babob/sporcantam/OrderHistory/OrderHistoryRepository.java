package com.babob.sporcantam.OrderHistory;

import com.babob.sporcantam.ViewHistory.ViewHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface OrderHistoryRepository extends CrudRepository<OrderHistory, Integer>  {
    @Query(
            value = "SELECT sale_id FROM order_history o WHERE o.customer_email = ?1",nativeQuery = true)
    Collection<String> findSaleIdsByCustomerEmail(String customer_email);
}

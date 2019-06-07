package com.babob.sporcantam.ViewHistory;
import com.babob.sporcantam.ViewHistory.ViewHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface ViewHistoryRepository extends CrudRepository<ViewHistory, Integer> {
    @Query(
        value = "SELECT UUID FROM viewhistory v WHERE v.customer_email = ?1",nativeQuery = true)
    Collection<String> findUUIDsByCustomerEmail(String customer_email);
}

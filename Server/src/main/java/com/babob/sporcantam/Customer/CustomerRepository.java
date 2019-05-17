package com.babob.sporcantam.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query(
            value = "SELECT * FROM customer u WHERE u.sessionid = ?1",nativeQuery = true)
    Collection<Customer> findBySessionID(String sessionID);

    @Query(
            value = "SELECT * FROM customer u WHERE u.email = ?1",nativeQuery = true)
    Collection<Customer> findByEmail(String email);
}
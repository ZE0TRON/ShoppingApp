package com.babob.sporcantam.Seller;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SellerRepository extends CrudRepository<Seller, Integer>{
    @Query(
            value = "SELECT * FROM seller u WHERE u.sessionid = ?1",nativeQuery = true)
    Collection<Seller> findBySessionID(String sessionID);

    @Query(
            value = "SELECT * FROM seller u WHERE u.id = ?1",nativeQuery = true)
    Collection<Seller> findBySellerID(Long sellerID);

    @Query(
            value = "SELECT * FROM seller u WHERE u.email = ?1",nativeQuery = true)
    Collection<Seller> findByEmail(String email);
    @Query(
            value = "SELECT * FROM seller u WHERE u.company_name = ?1",nativeQuery = true)
    Collection<Seller> findByCompanyName(String company_name);
}

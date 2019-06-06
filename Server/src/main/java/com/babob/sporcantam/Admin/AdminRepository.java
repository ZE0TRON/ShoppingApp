package com.babob.sporcantam.Admin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;



@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer> {
    @Query(
            value = "SELECT * FROM admin u WHERE u.sessionid = ?1",nativeQuery = true)
    Collection<Admin> findBySessionID(String sessionID);

    @Query(
            value = "SELECT * FROM admin u WHERE u.email = ?1",nativeQuery = true)
    Collection<Admin> findByEmail(String email);


}



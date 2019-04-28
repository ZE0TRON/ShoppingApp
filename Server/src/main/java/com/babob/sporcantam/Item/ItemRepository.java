package com.babob.sporcantam.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer>{
    @Query(
            value = "SELECT * FROM item i WHERE i.item_title = item_title", nativeQuery = true)
    Collection<Item> findByTitle(String item_title);

    @Query(
            value = "SELECT * FROM item c WHERE c.UUID = uuid", nativeQuery = true)
    Collection<Item> findByUUID(String uuid);

    @Query(
            value = "SELECT * FROM item i WHERE i.seller = seller", nativeQuery = true)
    Collection<Item> findBySeller(String seller);
    @Query(
            value = "DELETE  FROM item i WHERE i.UUID = UUID", nativeQuery = true)
    void deleteByUUID(String UUID);
}

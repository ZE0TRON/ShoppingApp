package com.babob.sporcantam.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer>{
    @Query(
            value = "SELECT * FROM item i WHERE i.item_title = ?1", nativeQuery = true)
    Collection<Item> findByTitle(String item_title);

    @Query(
            value = "SELECT * FROM item c WHERE c.UUID = ?1", nativeQuery = true)
    Collection<Item> findByUUID(String uuid);

    @Query(
            value = "SELECT * FROM item i WHERE i.seller = ?1", nativeQuery = true)
    Collection<Item> findBySeller(String seller);

    @Query(
            value = "SELECT * FROM item", nativeQuery = true)
    Collection<Item> getAllItems();

    @Query(
            value = "DELETE  FROM item i WHERE i.UUID = ?1", nativeQuery = true)
    void deleteByUUID(String UUID);

    @Query(
            value = "SELECT * FROM item i WHERE i.category = ?1", nativeQuery = true)
    Collection<Item> getItemsByCategory(String category);

}

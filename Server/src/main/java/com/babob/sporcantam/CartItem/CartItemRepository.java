package com.babob.sporcantam.CartItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
    @Query(
            value = "SELECT * FROM cart_item u WHERE u.shopping_cartid = ?1",nativeQuery = true)
    Collection<CartItem> getItemsById(Long id);

    @Query(
            value = "SELECT * FROM cart_item u WHERE u.shopping_cartid = ?2 AND u.uuid = ?1",nativeQuery = true)
    Collection<CartItem> getCartItems(String itemId,Long cartID);
}




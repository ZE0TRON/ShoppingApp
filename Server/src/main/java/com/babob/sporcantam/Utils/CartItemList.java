package com.babob.sporcantam.Utils;


import com.babob.sporcantam.CartItem.CartItem;

import java.util.Collection;

public class CartItemList {
    Collection<CartItem> items;
    public CartItemList(Collection<CartItem> items) {
        this.items = items;
    }

    public void setItems(Collection<CartItem> items) {
        this.items = items;
    }

    public Collection<CartItem> getItems() {
        return items;
    }
}

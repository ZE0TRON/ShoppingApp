package com.babob.sporcantam.Utils;


import com.babob.sporcantam.CartItem.CartItem;
import com.babob.sporcantam.Customer.Customer;
import com.babob.sporcantam.Seller.Seller;

import java.util.Collection;

public class SellerList {
    Collection<Seller> items;
    public SellerList(Collection<Seller> items) {
        this.items = items;
    }

    public void setItems(Collection<Seller> items) {
        this.items = items;
    }

    public Collection<Seller> getItems() {
        return items;
    }
}

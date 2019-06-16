package com.babob.sporcantam.Utils;


import com.babob.sporcantam.CartItem.CartItem;
import com.babob.sporcantam.Customer.Customer;
import com.babob.sporcantam.Order.Orders;

import java.util.Collection;

public class OrderList {
    Collection<Orders> items;
    public OrderList(Collection<Orders> items) {
        this.items = items;
    }

    public void setItems(Collection<Orders> items) {
        this.items = items;
    }

    public Collection<Orders> getItems() {
        return items;
    }
}

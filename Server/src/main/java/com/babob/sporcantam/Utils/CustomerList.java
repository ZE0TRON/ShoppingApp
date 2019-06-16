package com.babob.sporcantam.Utils;


import com.babob.sporcantam.CartItem.CartItem;
import com.babob.sporcantam.Customer.Customer;

import java.util.Collection;

public class CustomerList {
    Collection<Customer> items;
    public CustomerList(Collection<Customer> items) {
        this.items = items;
    }

    public void setItems(Collection<Customer> items) {
        this.items = items;
    }

    public Collection<Customer> getItems() {
        return items;
    }
}

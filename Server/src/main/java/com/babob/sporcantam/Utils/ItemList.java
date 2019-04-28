package com.babob.sporcantam.Utils;

import com.babob.sporcantam.Item.Item;

import java.util.Collection;

public class ItemList {
    Collection<Item> items;
    public ItemList(Collection<Item> items) {
        this.items = items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public Collection<Item> getItems() {
        return items;
    }
}

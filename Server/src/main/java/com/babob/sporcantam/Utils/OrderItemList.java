package com.babob.sporcantam.Utils;
import com.babob.sporcantam.OrderItem.OrderItem;
import java.util.Collection;

public class OrderItemList {
    Collection<OrderItem> items;
    public OrderItemList(Collection<OrderItem> items) {
        this.items = items;
    }

    public void setItems(Collection<OrderItem> items) {
        this.items = items;
    }

    public Collection<OrderItem> getItems() {
        return items;
    }
}

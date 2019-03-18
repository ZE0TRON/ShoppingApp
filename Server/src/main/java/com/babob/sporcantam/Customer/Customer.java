package com.babob.sporcantam.Customer;
import com.babob.sporcantam.User.User;


public class Customer extends User{
    private String address;
    private int cartID;
    private int orderListID;

    public int getCartID() {
        return cartID;
    }

    public String getAddress() {
        return address;
    }

    public int getOrderListID() {
        return orderListID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public void setOrderListID(int orderListID) {
        this.orderListID = orderListID;
    }

    @Override
    public String toString() {
        String string = "";
        string = string + "Cart ID :"+cartID+"\n Address: "+address+"\n Order List ID : "+orderListID+"\n Cart ID: "+cartID+"\n";
        return super.toString()+string;
    }
}

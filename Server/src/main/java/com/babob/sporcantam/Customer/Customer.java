package com.babob.sporcantam.Customer;
import com.babob.sporcantam.User.User;
import javax.persistence.Entity;

@Entity
public class Customer extends User{
    private Integer id;
    private String address;
    private Integer cartID;
    private Integer orderListID;

    public Integer getCartID() {
        return cartID;
    }

    public String getAddress() {
        return address;
    }

    public Integer getOrderListID() {
        return orderListID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCartID(Integer cartID) {
        this.cartID = cartID;
    }

    public void setOrderListID(Integer orderListID) {
        this.orderListID = orderListID;
    }

    @Override
    public String toString() {
        String string = "";
        string = string + "Cart ID :"+cartID+"\n Address: "+address+"\n Order List ID : "+orderListID+"\n Cart ID: "+cartID+"\n";
        return super.toString()+string;
    }

}

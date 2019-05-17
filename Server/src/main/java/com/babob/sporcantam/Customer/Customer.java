package com.babob.sporcantam.Customer;
import com.babob.sporcantam.User.User;
import javax.persistence.Entity;

@Entity
public class Customer extends User{
    private String address;
    private Long cartID;
    private Long orderListID;

    public Long getCartID() {
        return cartID;
    }

    public String getAddress() {
        return address;
    }

    public Long getOrderListID() {
        return orderListID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCartID(Long cartID) {
        this.cartID = cartID;
    }

    public void setOrderListID(Long orderListID) {
        this.orderListID = orderListID;
    }

    @Override
    public String toString() {
        String string = "";
        string = string + "CartItem ID :"+cartID+"\n Address: "+address+"\n Order List ID : "+orderListID+"\n CartItem ID: "+cartID+"\n";
        return super.toString()+string;
    }

}

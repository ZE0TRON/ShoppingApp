package com.babob.sporcantam.Order;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Orders {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orders(){
        this.is_confirmed = 0;
    }
    public String getCustomerEmail() {
        return customer_email;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customer_email = customerEmail;
    }



    public void setConfirmed(int confirmed) {
        this.is_confirmed = confirmed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String customer_email;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    private String order_id;

    public Integer getIs_confirmed() {
        return is_confirmed;
    }

    private Integer is_confirmed;

}
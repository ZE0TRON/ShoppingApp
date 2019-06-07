package com.babob.sporcantam.ViewHistory;
import javax.persistence.*;

@Entity
public class ViewHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String customer_email;
    private String UUID;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        String string = "Customer e-mail: " + customer_email + "\nUUID: " + UUID;
        return string;
    }
}



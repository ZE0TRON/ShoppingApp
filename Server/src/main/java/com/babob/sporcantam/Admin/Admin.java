package com.babob.sporcantam.Admin;
import com.babob.sporcantam.Admin.Admin;
import com.babob.sporcantam.User.User;

import javax.persistence.Entity;


@Entity
public class Admin extends User {
    private Integer employeeID;

    public Integer getEmployeeID() {
        return employeeID;
    }


    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }
}

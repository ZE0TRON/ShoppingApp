package com.babob.sporcantam.Admin;
import com.babob.sporcantam.Admin.Admin;

public class Admin {
    private Integer access_level ;
    private Integer employeeID;

    public Integer getAccess_level() {
        return access_level;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setAccess_level(Integer access_level) {
        this.access_level = access_level;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }
}

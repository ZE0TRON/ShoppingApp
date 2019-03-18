package com.babob.sporcantam.Admin;
import com.babob.sporcantam.Admin.Admin;

public class Admin {
    private int access_level ;
    private int employeeID;

    public int getAccess_level() {
        return access_level;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setAccess_level(int access_level) {
        this.access_level = access_level;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
}

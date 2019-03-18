package com.babob.sporcantam.Seller;
import com.babob.sporcantam.User.User;
import javax.persistence.Entity;
;

@Entity
public class Seller extends User{
    private String company_name;
    private String phone_number;
    private String company_address;
    private String IBAN;

    public String getCompany_address() {
        return company_address;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return super.toString() + "\nCompany Name: "+ company_name + "\nCompany Address: " + company_address +
                "\nIBAN: "+ IBAN + "\nPhone Number: "+ phone_number +"\n";
    }
}
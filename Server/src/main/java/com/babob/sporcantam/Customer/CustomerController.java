package com.babob.sporcantam.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(path="/customer") // This means URL's start with /demo (after Application path)
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(method=POST,path="/add") // Map ONLY GET Requests
    public @ResponseBody
    String addCustomer (@CookieValue(name = "JSESSIONID") String sessionID, @RequestParam String email
            ,@RequestParam String password, @RequestParam String first_name,@RequestParam String last_name,@RequestParam String userType){
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirst_name(first_name);
        customer.setLast_name(last_name);
        customer.setPassword(password);
        customer.setSessionID(sessionID);
        try {
            customerRepository.save(customer);
            return "success";
        }
        catch (Exception e){

            return "false"+e.getMessage();
        }
    }

    @RequestMapping(method=POST,path="/email") // Map ONLY GET Requests
    public @ResponseBody
    String getCustomer(@CookieValue(name = "JSESSIONID") String sessionID){
        Collection<Customer> customerCollection = customerRepository.findBySessionID(sessionID);
        Customer customer = customerCollection.iterator().next();
        if (customer.getEmail()==null) {
           return "Login first";
        }
        return customer.getEmail();
    }

    @RequestMapping(method=POST,path="/login")
    public @ResponseBody
    String customerLogin(@RequestParam String email, @RequestParam String password,
                         @CookieValue(name = "JSESSIONID") String sessionID){
        try{
            Customer customer = customerRepository.findByEmail(email).iterator().next(); //get first (and oly) customer
            if(password.equals(customer.getPassword()) && sessionID.equals(customer.getSessionID()))
            {
                return "You have logged in successfully.";
            }

            else{
                return "Incorrect password!";
            }

        }
        catch(Exception e){
            return "This e-mail does not hold any account!";
        }


    }


}
